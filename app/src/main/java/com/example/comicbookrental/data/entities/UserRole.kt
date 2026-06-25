package com.example.comicbookrental.data.entities

import kotlinx.serialization.Serializable


@Serializable
enum class UserRole {
    USER,
    ADMIN;

    val isAdmin: Boolean get() = this == ADMIN
}
