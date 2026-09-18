package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.BarbershopData
import com.example.data.model.Appointment
import com.example.data.model.BarberService
import com.example.data.model.GroomingTip
import com.example.data.model.MasterBarber
import com.example.data.model.ServiceCategory
import com.example.data.repository.BarbershopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BookingFormState(
    val selectedServices: List<BarberService> = emptyList(),
    val selectedBarber: MasterBarber? = null,
    val selectedDate: String = "Today",
    val selectedTime: String = "11:00 AM",
    val hairPreference: String = "Classic Hair Cut",
    val beardPreference: String = "Beard Trimmer",
    val customerName: String = "",
    val customerPhone: String = "",
    val notes: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
)

class BarbershopViewModel(private val repository: BarbershopRepository) : ViewModel() {

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
    val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _showBookingSheet = MutableStateFlow(false)
    val showBookingSheet: StateFlow<Boolean> = _showBookingSheet.asStateFlow()

    private val _bookingForm = MutableStateFlow(BookingFormState())
    val bookingForm: StateFlow<BookingFormState> = _bookingForm.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage.asStateFlow()

    val appointments: StateFlow<List<Appointment>> = repository.allAppointments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val masterBarbers: List<MasterBarber> = repository.getAllBarbers()
    val groomingTips: List<GroomingTip> = repository.getGroomingTips()

    init {
        viewModelScope.launch {
            // Seed a demo booking if table is empty for instant rich preview
            val currentList = repository.allAppointments.firstOrNull()
            if (currentList == null || currentList.isEmpty()) {
                repository.seedInitialBookingIfEmpty()
            }
        }
    }

    fun selectTab(tab: Int) {
        _selectedTab.value = tab
    }

    fun selectCategory(category: ServiceCategory) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredServices(): List<BarberService> {
        return repository.getServices(_selectedCategory.value, _searchQuery.value)
    }

    fun openBookingForService(service: BarberService) {
        val defaultBarber = masterBarbers.firstOrNull { it.isAvailableToday } ?: masterBarbers.first()
        _bookingForm.value = BookingFormState(
            selectedServices = listOf(service),
            selectedBarber = defaultBarber,
            selectedDate = "Today",
            selectedTime = "2:30 PM",
            hairPreference = if (service.includesHair) "Classic Hair Cut" else "Not required",
            beardPreference = if (service.includesBeard) "Beard Trimmer" else "Not required",
            customerName = "",
            customerPhone = "",
            notes = ""
        )
        _showBookingSheet.value = true
    }

    fun openBookingForBarber(barber: MasterBarber) {
        val defaultService = BarbershopData.services.first()
        _bookingForm.value = BookingFormState(
            selectedServices = listOf(defaultService),
            selectedBarber = barber,
            selectedDate = "Today",
            selectedTime = "3:00 PM",
            hairPreference = "Classic Hair Cut",
            beardPreference = "Beard Trimmer",
            customerName = "",
            customerPhone = "",
            notes = ""
        )
        _showBookingSheet.value = true
    }

    fun openCustomBooking() {
        val defaultBarber = masterBarbers.firstOrNull { it.isAvailableToday } ?: masterBarbers.first()
        val defaultService = BarbershopData.services.first()
        _bookingForm.value = BookingFormState(
            selectedServices = listOf(defaultService),
            selectedBarber = defaultBarber,
            selectedDate = "Today",
            selectedTime = "11:00 AM",
            hairPreference = "Classic Hair Cut",
            beardPreference = "Beard Trimmer"
        )
        _showBookingSheet.value = true
    }

    fun dismissBookingSheet() {
        _showBookingSheet.value = false
    }

    fun toggleServiceInBooking(service: BarberService) {
        val current = _bookingForm.value.selectedServices.toMutableList()
        if (current.any { it.id == service.id }) {
            if (current.size > 1) {
                current.removeAll { it.id == service.id }
            }
        } else {
            current.add(service)
        }
        _bookingForm.value = _bookingForm.value.copy(selectedServices = current)
    }

    fun updateBookingBarber(barber: MasterBarber) {
        _bookingForm.value = _bookingForm.value.copy(selectedBarber = barber)
    }

    fun updateBookingDate(date: String) {
        _bookingForm.value = _bookingForm.value.copy(selectedDate = date)
    }

    fun updateBookingTime(time: String) {
        _bookingForm.value = _bookingForm.value.copy(selectedTime = time)
    }

    fun updateHairPreference(pref: String) {
        _bookingForm.value = _bookingForm.value.copy(hairPreference = pref)
    }

    fun updateBeardPreference(pref: String) {
        _bookingForm.value = _bookingForm.value.copy(beardPreference = pref)
    }

    fun updateCustomerName(name: String) {
        _bookingForm.value = _bookingForm.value.copy(customerName = name, errorMessage = null)
    }

    fun updateCustomerPhone(phone: String) {
        _bookingForm.value = _bookingForm.value.copy(customerPhone = phone, errorMessage = null)
    }

    fun updateCustomerNotes(notes: String) {
        _bookingForm.value = _bookingForm.value.copy(notes = notes)
    }

    fun confirmBooking() {
        val form = _bookingForm.value
        val name = form.customerName.trim()
        val phone = form.customerPhone.trim()

        if (name.isBlank()) {
            _bookingForm.value = form.copy(errorMessage = "Please enter your name")
            return
        }
        if (phone.isBlank()) {
            _bookingForm.value = form.copy(errorMessage = "Please enter your phone number")
            return
        }

        viewModelScope.launch {
            _bookingForm.value = form.copy(isSubmitting = true)

            val totalDuration = form.selectedServices.sumOf { it.durationMinutes }
            val totalPrice = form.selectedServices.sumOf { it.price }
            val serviceNames = form.selectedServices.joinToString(" + ") { it.title }
            val barberName = form.selectedBarber?.name ?: "Any Master Barber"

            val appointment = Appointment(
                customerName = name,
                customerPhone = phone,
                serviceNames = serviceNames,
                totalPrice = totalPrice,
                totalDurationMinutes = totalDuration,
                barberName = barberName,
                appointmentDate = form.selectedDate,
                appointmentTime = form.selectedTime,
                hairPreference = form.hairPreference,
                beardPreference = form.beardPreference,
                notes = form.notes,
                status = "Confirmed"
            )

            repository.bookAppointment(appointment)
            _showBookingSheet.value = false
            _snackBarMessage.value = "Appointment booked for $name with $barberName!"
            // Switch to appointments tab to let user see their booking
            _selectedTab.value = 2
        }
    }

    fun updateAppointmentStatus(appointmentId: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, newStatus)
            _snackBarMessage.value = "Status updated to $newStatus"
        }
    }

    fun cancelAppointment(appointmentId: Long) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(appointmentId, "Cancelled")
            _snackBarMessage.value = "Appointment cancelled"
        }
    }

    fun deleteAppointment(appointmentId: Long) {
        viewModelScope.launch {
            repository.deleteAppointment(appointmentId)
            _snackBarMessage.value = "Appointment removed"
        }
    }

    fun clearSnackBar() {
        _snackBarMessage.value = null
    }

    companion object {
        fun provideFactory(repository: BarbershopRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BarbershopViewModel(repository) as T
                }
            }
    }
}
