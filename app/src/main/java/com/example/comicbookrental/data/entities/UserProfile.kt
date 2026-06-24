package com.example.comicbookrental.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "1",
    val heroName: String = "STORM BLAZE",
    val realName: String = "Comic Renter",
    val email: String = "storm.blaze@panelrush.com",
    val rank: String = "HEROIC",
    val phone: String = "+1 (555) 012-9844",
    val region: String = "Metropolis East, US",
    val rentedCount: Int = 128,
    val activeCount: Int = 14,
    val avatarUrl: String = "",
    val isEmailVerified: Boolean = false
)
