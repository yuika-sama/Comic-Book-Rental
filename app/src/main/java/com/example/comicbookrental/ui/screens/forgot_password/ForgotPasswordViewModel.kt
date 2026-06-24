package com.example.comicbookrental.ui.screens.forgot_password

import android.util.Patterns
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
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailErrorMessage = null, errorMessage = null) }
    }

    fun sendOtp() {
        val currentState = _uiState.value

        if (currentState.isLoading) return

        if (!validateForm(currentState)) return

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.sendOtp(currentState.email)

            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }

    private fun validateForm(currentState: ForgotPasswordUiState): Boolean {
        var isValid = true

        if (currentState.email.isBlank()) {
            _uiState.update { it.copy(emailErrorMessage = "Email is required") }
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
            _uiState.update { it.copy(emailErrorMessage = "Invalid email format") }
            isValid = false
        }

        return isValid
    }

    fun resetSuccessState() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}
