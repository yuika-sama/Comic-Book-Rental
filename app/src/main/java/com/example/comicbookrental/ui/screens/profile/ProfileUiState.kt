package com.example.comicbookrental.ui.screens.profile

import com.example.comicbookrental.data.entities.User


data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
