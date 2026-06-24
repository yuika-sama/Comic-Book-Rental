package com.example.comicbookrental.ui.screens.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState.asStateFlow()

    fun initEmail(email: String) {
        if (_uiState.value.email != email) {
            _uiState.update { it.copy(email = email) }
        }
    }

    fun onNewPasswordChange(newValue: String) {
        val (strength, label) = evaluatePasswordStrength(newValue)
        _uiState.update {
            it.copy(
                newPassword = newValue,
                newPasswordErrorMessage = null,
                errorMessage = null,
                passwordStrength = strength,
                passwordStrengthLabel = label
            )
        }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordShow = !it.isPasswordShow) }
    }

    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordShow = !it.isConfirmPasswordShow) }
    }

    private fun evaluatePasswordStrength(password: String): Pair<Int, String> {
        if (password.isEmpty()) return Pair(0, "Too weak")
        var score = 0
        if (password.length >= 8) score += 1
        if (password.any { it.isDigit() }) score += 1
        if (password.any { it.isUpperCase() } && password.any { it.isLowerCase() }) score += 1
        if (password.any { !it.isLetterOrDigit() }) score += 1

        val strength = if (score < 1) 1 else score
        val label = when (strength) {
            1 -> "Weak"
            2 -> "Medium"
            3 -> "Strong"
            4 -> "Almost invincible"
            else -> "Weak"
        }
        return Pair(strength, label)
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(
                confirmPassword = newValue,
                confirmPasswordErrorMessage = null,
                errorMessage = null
            )
        }
    }

    fun resetPassword() {
        val currentState = _uiState.value
        if (currentState.isLoading) return

        if (!validateForm(currentState)) return

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.resetPassword(currentState.email, currentState.newPassword)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Failed to reset password"
                        )
                    }
                }
            )
        }
    }

    private fun validateForm(currentState: ResetPasswordUiState): Boolean {
        var isValid = true

        if (currentState.newPassword.isEmpty()) {
            _uiState.update { it.copy(newPasswordErrorMessage = "Password is required") }
            isValid = false
        } else if (currentState.newPassword.length < 8) {
            _uiState.update { it.copy(newPasswordErrorMessage = "Password must be at least 8 characters") }
            isValid = false
        }

        if (currentState.confirmPassword.isEmpty()) {
            _uiState.update { it.copy(confirmPasswordErrorMessage = "Please confirm your password") }
            isValid = false
        } else if (currentState.confirmPassword != currentState.newPassword) {
            _uiState.update { it.copy(confirmPasswordErrorMessage = "Passwords do not match") }
            isValid = false
        }

        return isValid
    }

    fun resetSuccessState() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
