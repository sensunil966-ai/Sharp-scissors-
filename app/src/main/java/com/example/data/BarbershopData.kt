package com.example.data

import com.example.data.model.BarberService
import com.example.data.model.GroomingTip
import com.example.data.model.MasterBarber
import com.example.data.model.ServiceCategory

object BarbershopData {
    val services = listOf(
        // Hair cut
        BarberService(
            id = "hair_cut",
            title = "Hair Cut",
            category = ServiceCategory.HAIR,
            price = 100.0,
            durationMinutes = 30,
            description = "Expert precision haircut, scissor styling & clean neck finish.",
            isCombo = false,
            isBestseller = true,
            includesHair = true,
            includesBeard = false,
            iconName = "scissors"
        ),

        // Beard services
        BarberService(
            id = "beard_shave",
            title = "Beard Shave",
            category = ServiceCategory.BEARD,
            price = 50.0,
            durationMinutes = 20,
            description = "Clean smooth straight razor shave with warm lather and aftershave splash.",
            isCombo = false,
            isBestseller = false,
            includesHair = false,
            includesBeard = true,
            iconName = "razor"
        ),
        BarberService(
            id = "beard_trimmer",
            title = "Beard Trimmer",
            category = ServiceCategory.BEARD,
            price = 50.0,
            durationMinutes = 15,
            description = "Precision trimmer beard shaping, length setting, and cheek edge definition.",
            isCombo = false,
            isBestseller = true,
            includesHair = false,
            includesBeard = true,
            iconName = "beard"
        ),

        // Massage
        BarberService(
            id = "massage_normal",
            title = "Normal Massage",
            category = ServiceCategory.MASSAGE,
            price = 70.0,
            durationMinutes = 20,
            description = "Relaxing head and face pressure point massage to relieve stress and tension.",
            isCombo = false,
            isBestseller = false,
            includesHair = true,
            includesBeard = false,
            iconName = "spa"
        ),
        BarberService(
            id = "massage_scrub",
            title = "Scrub Massage",
            category = ServiceCategory.MASSAGE,
            price = 100.0,
            durationMinutes = 25,
            description = "Deep exfoliating scrub massage removing dead cells, dirt and rejuvenating skin.",
            isCombo = false,
            isBestseller = true,
            includesHair = false,
            includesBeard = false,
            iconName = "spa"
        ),

        // Detan
        BarberService(
            id = "detan_oxyglow",
            title = "Oxyglow Detan",
            category = ServiceCategory.DETAN,
            price = 150.0,
            durationMinutes = 25,
            description = "Oxyglow active oxygen tan removal pack for radiant, even-toned skin.",
            isCombo = false,
            isBestseller = false,
            includesHair = false,
            includesBeard = false,
            iconName = "mask"
        ),
        BarberService(
            id = "detan_raaga",
            title = "Raaga Detan",
            category = ServiceCategory.DETAN,
            price = 200.0,
            durationMinutes = 30,
            description = "Professional Raaga herbal de-tan treatment with kojic acid and milk extracts.",
            isCombo = false,
            isBestseller = true,
            includesHair = false,
            includesBeard = false,
            iconName = "mask"
        ),
        BarberService(
            id = "detan_o3_plus",
            title = "O3+ Detan",
            category = ServiceCategory.DETAN,
            price = 250.0,
            durationMinutes = 35,
            description = "Advanced dermatological O3+ de-tan brightening treatment with instant glow.",
            isCombo = false,
            isBestseller = true,
            includesHair = false,
            includesBeard = false,
            iconName = "mask"
        ),

        // Facial
        BarberService(
            id = "facial_lotus",
            title = "Lotus Facial",
            category = ServiceCategory.FACIAL,
            price = 600.0,
            durationMinutes = 45,
            description = "Natural herbal Lotus skincare facial including cleansing, scrub, cream massage & pack.",
            isCombo = false,
            isBestseller = false,
            includesHair = false,
            includesBeard = false,
            iconName = "spa"
        ),
        BarberService(
            id = "facial_oxyglow",
            title = "Oxyglow Facial",
            category = ServiceCategory.FACIAL,
            price = 800.0,
            durationMinutes = 50,
            description = "Enriched Oxyglow brightening facial for deep nourishment, hydration and natural radiance.",
            isCombo = false,
            isBestseller = true,
            includesHair = false,
            includesBeard = false,
            iconName = "spa"
        ),
        BarberService(
            id = "facial_lotus_premium",
            title = "Lotus Premium Facial",
            category = ServiceCategory.FACIAL,
            price = 1200.0,
            durationMinutes = 60,
            description = "Luxury Lotus Professional treatment for intense rejuvenation, glow and youthful skin firming.",
            isCombo = false,
            isBestseller = true,
            includesHair = false,
            includesBeard = false,
            iconName = "crown"
        ),

        // Hair colour
        BarberService(
            id = "colour_cosmo",
            title = "Cosmo Hair Color",
            category = ServiceCategory.HAIR_COLOUR,
            price = 150.0,
            durationMinutes = 35,
            description = "Natural black/brown Cosmo hair color with rich shine and complete grey coverage.",
            isCombo = false,
            isBestseller = false,
            includesHair = true,
            includesBeard = false,
            iconName = "color"
        ),
        BarberService(
            id = "colour_matrix",
            title = "Matrix Hair Color",
            category = ServiceCategory.HAIR_COLOUR,
            price = 250.0,
            durationMinutes = 40,
            description = "Professional salon-grade Matrix SoColor long-lasting hair color with conditioning.",
            isCombo = false,
            isBestseller = true,
            includesHair = true,
            includesBeard = false,
            iconName = "color"
        ),
        BarberService(
            id = "colour_loreal",
            title = "L'Oreal Hair Color",
            category = ServiceCategory.HAIR_COLOUR,
            price = 500.0,
            durationMinutes = 45,
            description = "Premium L'Oreal Professionnel Majirel rich permanent hair color with deep gloss & care.",
            isCombo = false,
            isBestseller = true,
            includesHair = true,
            includesBeard = false,
            iconName = "crown"
        )
    )

    val masterBarbers = listOf(
        MasterBarber(
            id = "barber_1",
            name = "Rahul Sen",
            nickname = "Master Stylist",
            specialty = "Hair Cuts, Fades & Styling",
            experienceYears = 8,
            rating = 4.95,
            reviewCount = 380,
            isAvailableToday = true,
            bio = "Master hairstylist specializing in precision modern hair cuts, creative styling, and flawless finishes."
        ),
        MasterBarber(
            id = "barber_2",
            name = "Sunil Kumar Sen",
            nickname = "Grooming Specialist",
            specialty = "Beard Shaping, Shave & Spa",
            experienceYears = 10,
            rating = 4.98,
            reviewCount = 450,
            isAvailableToday = true,
            bio = "Expert in traditional straight razor shaves, beard trimming, soothing massages and premium facials."
        )
    )

    val groomingTips = listOf(
        GroomingTip(
            title = "Hair Cut & Beard Maintenance",
            category = "Hair & Beard",
            summary = "Keep your haircut and beard looking neat and sharp every week.",
            details = "Regular hair cuts every 3-4 weeks maintain clean edges, while regular beard trimming or clean shaving keeps your facial geometry polished and fresh.",
            recommendedServices = listOf("Hair Cut", "Beard Trimmer", "Beard Shave")
        ),
        GroomingTip(
            title = "Detan & Skin Care Rituals",
            category = "Skin Care",
            summary = "Remove sun tan, pollution damage and dullness with regular Detan.",
            details = "Sun exposure causes uneven skin tone and pigmentation. An Oxyglow, Raaga or O3+ Detan every 2 weeks gently restores natural brightness and clears pores.",
            recommendedServices = listOf("Raaga Detan", "O3+ Detan")
        ),
        GroomingTip(
            title = "Facial Glow & Rejuvenation",
            category = "Facial",
            summary = "Recharge your skin with natural hydration and deep cleansing.",
            details = "Facials cleanse dirt deep from the dermis, improve blood circulation, and give a healthy radiant glow before important occasions and celebrations.",
            recommendedServices = listOf("Lotus Facial", "Oxyglow Facial", "Lotus Premium Facial")
        ),
        GroomingTip(
            title = "Hair Color Care & Longevity",
            category = "Hair Color",
            summary = "How to preserve professional color and complete grey coverage.",
            details = "Use color-safe sulfate-free shampoo and avoid hot water washes directly on hair to keep Matrix and L'Oreal color vibrant for weeks.",
            recommendedServices = listOf("Matrix Hair Color", "L'Oreal Hair Color")
        )
    )

    val shopInfo = mapOf(
        "name" to "Sharp Scissors",
        "tagline" to "Professional Men's Salon & Hair Styling",
        "address" to "Jk cricle Kankroli Rajsamnd 313324",
        "hours" to "Mon - Sun: 8:00 AM - 9:00 PM",
        "phone1" to "8000536544",
        "phone2" to "7737591386",
        "contacts" to "8000536544, 7737591386"
    )
}
