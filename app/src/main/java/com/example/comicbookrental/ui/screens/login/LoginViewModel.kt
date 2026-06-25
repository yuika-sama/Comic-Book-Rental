package com.example.comicbookrental.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.rememberUpdatedState
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
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel()
{
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String)
    {
        _uiState.update { it.copy(email = newValue, emailErrorMessage = "", errorMessage = "") }
    }

    fun onPasswordChange(newValue: String)
    {
        _uiState.update {
            it.copy(
                password = newValue,
                passwordErrorMessage = "",
                errorMessage = ""
            )
        }
    }

    fun login()
    {
        val currentState = _uiState.value

        if (currentState.isLoading) return

        if (!validateForm(currentState)) return

        _uiState.update { it.copy(isLoading = true, errorMessage = "") }

        viewModelScope.launch {
            val result = repository.login(currentState.email, currentState.password, currentState.rememberMe)

            result.fold(
                onSuccess = { isVerified ->
                    if (isVerified){
                        val isAdmin = repository.getCurrentRole().isAdmin
                        _uiState.update { it.copy(isLoading = false, isSuccess = true, isAdmin = isAdmin) }
                    } else {
                        repository.sendOtp(currentState.email)
                        _uiState.update { it.copy(isLoading = false, requiresVerification = true) }
                    }
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

    fun onOAuthLogin()
    {
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

    fun onRememberMeChange(){
        _uiState.update { it.copy(rememberMe = !it.rememberMe) }
    }

    private fun validateForm(currentState: LoginUiState): Boolean
    {
        var isValid = true

        if (currentState.email.isBlank() || currentState.email.isEmpty())
        {
            _uiState.update { it.copy(emailErrorMessage = "Email is required") }
            isValid = false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches())
        {
            _uiState.update { it.copy(emailErrorMessage = "Invalid email format") }
            isValid = false
        }

        if (currentState.password.isBlank() || currentState.password.isEmpty())
        {
            _uiState.update { it.copy(passwordErrorMessage = "Password is required") }
            isValid = false
        }
        else if (currentState.password.length < 8)
        {
            _uiState.update { it.copy(passwordErrorMessage = "Password must be at least 8 characters") }
            isValid = false
        }

        return isValid
    }

    fun resetSuccessState()
    {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun resetVerificationState(){
        _uiState.update { it.copy(requiresVerification = false) }
    }
}