package com.example.comicbookrental.ui.screens.forgot_password

data class ForgotPasswordUiState(
    val email: String = "",
    val emailErrorMessage: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)
