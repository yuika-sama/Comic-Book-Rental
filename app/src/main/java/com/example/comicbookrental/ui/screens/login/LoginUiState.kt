package com.example.comicbookrental.ui.screens.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val errorMessage: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)
