package com.example.data.repository

import com.example.data.BarbershopData
import com.example.data.db.AppointmentDao
import com.example.data.model.Appointment
import com.example.data.model.BarberService
import com.example.data.model.GroomingTip
import com.example.data.model.MasterBarber
import com.example.data.model.ServiceCategory
import kotlinx.coroutines.flow.Flow

class BarbershopRepository(private val appointmentDao: AppointmentDao) {

    val allAppointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()

    fun getServices(category: ServiceCategory = ServiceCategory.ALL, query: String = ""): List<BarberService> {
        return BarbershopData.services.filter { service ->
            val matchesCategory = (category == ServiceCategory.ALL) || (service.category == category)
            val matchesQuery = query.isBlank() ||
                    service.title.contains(query, ignoreCase = true) ||
                    service.description.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    fun getAllBarbers(): List<MasterBarber> = BarbershopData.masterBarbers

    fun getGroomingTips(): List<GroomingTip> = BarbershopData.groomingTips

    suspend fun bookAppointment(appointment: Appointment): Long {
        return appointmentDao.insertAppointment(appointment)
    }

    suspend fun updateAppointmentStatus(id: Long, newStatus: String) {
        appointmentDao.updateStatus(id, newStatus)
    }

    suspend fun deleteAppointment(id: Long) {
        appointmentDao.deleteAppointment(id)
    }

    suspend fun seedInitialBookingIfEmpty() {
        // Sample starter appointment to demonstrate active status
        val demoAppointment = Appointment(
            customerName = "Vikas Sharma",
            customerPhone = "9829012345",
            serviceNames = "Hair Cut + Beard Trimmer",
            totalPrice = 150.0,
            totalDurationMinutes = 45,
            barberName = "Rahul Sen (Master Stylist)",
            appointmentDate = "Today",
            appointmentTime = "4:30 PM",
            hairPreference = "Stylish Hair Cut",
            beardPreference = "Sharp Beard Trimmer",
            notes = "First visit at Sharp Scissors",
            status = "Confirmed"
        )
        appointmentDao.insertAppointment(demoAppointment)
    }
}
