package com.example.comicbookrental.ui.screens.register

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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel()
{
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newValue: String)
    {
        _uiState.update {
            it.copy(fullName = newValue)
        }
    }

    fun onEmailChange(newValue: String)
    {
        _uiState.update {
            it.copy(email = newValue)
        }
    }

    fun onPasswordChange(newValue: String)
    {
        _uiState.update {
            it.copy(password = newValue)
        }
    }

    fun onConfirmPasswordChange(newValue: String)
    {
        _uiState.update {
            it.copy(confirmPassword = newValue)
        }
    }

    fun onAcceptTermsChange(newValue: Boolean)
    {
        _uiState.update {
            it.copy(isAgreed = newValue)
        }
    }

    fun onTogglePasswordVisibility()
    {
        _uiState.update {
            it.copy(isViewPassword = !it.isViewPassword)
        }
    }

    fun onToggleConfirmPasswordVisibility()
    {
        _uiState.update {
            it.copy(isViewConfirmPassword = !it.isViewConfirmPassword)
        }
    }

    fun onSignUp()
    {
        resetErrorMessage()

        val currentState = _uiState.value

        if (currentState.isLoading) return

        if (!validateForm(currentState)) return

        _uiState.update { it.copy(isLoading = true, errorMessage = "") }

        viewModelScope.launch {
            val result = repository.register(
                currentState.fullName,
                currentState.email,
                currentState.password
            )
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }

    fun onOAuthLogin()
    {
        resetErrorMessage()

        _uiState.update { it.copy(isLoading = true, errorMessage = "") }

        viewModelScope.launch {
            val result = repository.oAuthLogin()
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

    private fun validateForm(currentState: RegisterUiState): Boolean
    {
        if (!currentState.isAgreed)
        {
            _uiState.update { it.copy(errorMessage = "You must agree to the terms and conditions") }
            return false
        }

        if (currentState.email.isBlank() || currentState.email.isEmpty())
        {
            _uiState.update { it.copy(emailError = "Email is required") }
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches())
        {
            _uiState.update { it.copy(emailError = "Invalid email format") }
            return false
        }

        if (currentState.password.isBlank() || currentState.password.isEmpty())
        {
            _uiState.update { it.copy(passwordError = "Password is required") }
            return false
        }
        else if (currentState.password.length < 8)
        {
            _uiState.update { it.copy(passwordError = "Password must be at least 8 characters") }
            return false
        }

        if (currentState.confirmPassword.isBlank() || currentState.confirmPassword.isEmpty())
        {
            _uiState.update { it.copy(confirmPasswordError = "Confirm password is required") }
            return false
        }
        else if (currentState.confirmPassword != currentState.password)
        {
            _uiState.update { it.copy(confirmPasswordError = "Passwords do not match") }
            return false
        }

        return true
    }

    fun resetErrorMessage()
    {
        _uiState.update {
            it.copy(
                errorMessage = "",
                emailError = "",
                passwordError = "",
                confirmPasswordError = ""
            )
        }
    }

    fun resetSuccessState()
    {
        _uiState.update { it.copy(isSuccess = false) }
    }
}