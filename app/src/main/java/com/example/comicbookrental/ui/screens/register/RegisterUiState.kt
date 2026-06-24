package com.example.comicbookrental.ui.screens.register

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isAgreed: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isViewPassword: Boolean = false,
    val isViewConfirmPassword: Boolean = false,
    val errorMessage: String = "",
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError: String = ""
)
