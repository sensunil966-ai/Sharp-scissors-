package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ServiceCategory(val displayName: String, val subtitle: String) {
    ALL("All", "Complete Salon Menu"),
    HAIR("Hair Cut", "Professional Cuts & Styling"),
    BEARD("Beard Care", "Shave, Trimmer & Edge Shaping"),
    MASSAGE("Massage", "Normal & Scrub Head/Face Massage"),
    DETAN("Detan", "Oxyglow, Raaga & O3+ Tan Removal"),
    FACIAL("Facial", "Lotus, Oxyglow & Premium Glow"),
    HAIR_COLOUR("Hair Colour", "Cosmo, Matrix & L'Oreal")
}

data class BarberService(
    val id: String,
    val title: String,
    val category: ServiceCategory,
    val price: Double,
    val durationMinutes: Int,
    val description: String,
    val isCombo: Boolean = false,
    val isBestseller: Boolean = false,
    val includesHair: Boolean = false,
    val includesBeard: Boolean = false,
    val iconName: String = "scissors"
)

data class MasterBarber(
    val id: String,
    val name: String,
    val nickname: String,
    val specialty: String,
    val experienceYears: Int,
    val rating: Double,
    val reviewCount: Int,
    val isAvailableToday: Boolean = true,
    val bio: String
)

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerName: String,
    val customerPhone: String,
    val serviceNames: String,
    val totalPrice: Double,
    val totalDurationMinutes: Int,
    val barberName: String,
    val appointmentDate: String,
    val appointmentTime: String,
    val hairPreference: String = "",
    val beardPreference: String = "",
    val notes: String = "",
    val status: String = "Confirmed", // Confirmed, In Chair, Completed, Cancelled
    val createdAt: Long = System.currentTimeMillis()
)

data class GroomingTip(
    val title: String,
    val category: String, // "Hair", "Beard", "Face Shape"
    val summary: String,
    val details: String,
    val recommendedServices: List<String>
)
