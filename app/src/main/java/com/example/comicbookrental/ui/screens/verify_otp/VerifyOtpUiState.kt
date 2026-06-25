package com.example.comicbookrental.ui.screens.verify_otp

data class VerifyOtpUiState(
    val email: String = "",
    val otpValues: List<String> = List(5) { "" },
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isAdmin: Boolean = false,
    val errorMessage: String? = null,
    val isResending: Boolean = false,
    val resendCooldown: Int = 0
)
