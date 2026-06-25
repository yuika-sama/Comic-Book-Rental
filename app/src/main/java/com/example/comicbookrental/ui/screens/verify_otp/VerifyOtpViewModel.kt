package com.example.comicbookrental.ui.screens.verify_otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyOtpUiState())
    val uiState: StateFlow<VerifyOtpUiState> = _uiState.asStateFlow()

    private var cooldownJob: Job? = null

    fun initEmail(email: String) {
        if (_uiState.value.email != email) {
            _uiState.update { it.copy(email = email) }
            startCooldownTimer()
        }
    }

    fun onOtpChange(index: Int, value: String) {
        _uiState.update { state ->
            val currentOtp = state.otpValues.toMutableList()
            if (index in currentOtp.indices) {
                currentOtp[index] = value
            }
            state.copy(otpValues = currentOtp, errorMessage = null)
        }
    }

    fun verifyOtp() {
        val currentState = _uiState.value
        if (currentState.isLoading) return

        val otpString = currentState.otpValues.joinToString("")
        if (otpString.length < 5) {
            _uiState.update {
                it.copy(
                    errorMessage = "Please enter all 5 digits of the OTP",
                    otpValues = List(5) { "" }
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.verifyOtp(currentState.email, otpString)
            result.fold(
                onSuccess = {
                    val isAdmin = repository.getCurrentRole().isAdmin
                    _uiState.update { it.copy(isLoading = false, isSuccess = true, isAdmin = isAdmin) }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Verification failed",
                            otpValues = List(5) { "" }
                        )
                    }
                }
            )
        }
    }

    fun resendOtp() {
        val currentState = _uiState.value
        if (currentState.isResending || currentState.resendCooldown > 0) return

        _uiState.update { it.copy(isResending = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.sendOtp(currentState.email)
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isResending = false,
                            otpValues = List(5) { "" }
                        )
                    }
                    startCooldownTimer()
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isResending = false,
                            errorMessage = exception.message ?: "Failed to resend OTP"
                        )
                    }
                }
            )
        }
    }

    private fun startCooldownTimer() {
        cooldownJob?.cancel()
        _uiState.update { it.copy(resendCooldown = 60) }
        cooldownJob = viewModelScope.launch {
            while (_uiState.value.resendCooldown > 0) {
                delay(1000L)
                _uiState.update { it.copy(resendCooldown = it.resendCooldown - 1) }
            }
        }
    }

    fun resetSuccessState() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    override fun onCleared() {
        super.onCleared()
        cooldownJob?.cancel()
    }
}
