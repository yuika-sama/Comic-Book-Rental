package com.example.comicbookrental.ui.screens.profile

import com.example.comicbookrental.data.models.UserProfile

data class ProfileUiState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
