package com.example.comicbookrental.data.entities


data class AdminUser(
    val email: String,
    val isBanned: Boolean,
    val isAdmin: Boolean,
)
