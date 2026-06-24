package com.example.comicbookrental.ui.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.domain.repository.AuthRepository
import com.example.comicbookrental.ui.utils.isStrongPassword
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
        var isValid = true
        if (!currentState.isAgreed)
        {
            _uiState.update { it.copy(errorMessage = "You must agree to the terms and conditions") }
            isValid = false
        }

        if (currentState.email.isBlank() || currentState.email.isEmpty())
        {
            _uiState.update { it.copy(emailError = "Email is required") }
            isValid = false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches())
        {
            _uiState.update { it.copy(emailError = "Invalid email format") }
            isValid = false
        }

        if (currentState.password.isBlank() || currentState.password.isEmpty())
        {
            _uiState.update { it.copy(passwordError = "Password is required") }
            isValid = false
        }
        else if (currentState.password.length < 8)
        {
            _uiState.update { it.copy(passwordError = "Password must be at least 8 characters") }
            isValid = false
        }
        else if (!isStrongPassword(currentState.password))
        {
            _uiState.update { it.copy(passwordError = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character") }
            isValid = false
        }

        if (currentState.confirmPassword.isBlank() || currentState.confirmPassword.isEmpty())
        {
            _uiState.update { it.copy(confirmPasswordError = "Confirm password is required") }
            isValid = false
        }
        else if (currentState.confirmPassword != currentState.password)
        {
            _uiState.update { it.copy(confirmPasswordError = "Passwords do not match") }
            isValid = false
        }

        return isValid
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