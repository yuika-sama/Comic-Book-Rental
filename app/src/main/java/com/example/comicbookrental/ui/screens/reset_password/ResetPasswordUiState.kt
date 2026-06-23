package com.example.comicbookrental.ui.screens.reset_password

data class ResetPasswordUiState(
    val email: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val newPasswordErrorMessage: String? = null,
    val confirmPasswordErrorMessage: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val passwordStrength: Int = 0,
    val passwordStrengthLabel: String = "Too weak",
    val isPasswordShow: Boolean = false,
    val isConfirmPasswordShow: Boolean = false
)
