package com.example.comicbookrental.ui.screens.profile

import com.example.comicbookrental.data.models.User

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
