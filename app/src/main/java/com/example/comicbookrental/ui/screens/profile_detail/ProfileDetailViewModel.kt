package com.example.comicbookrental.ui.screens.profile_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileDetailUiState())
    val uiState: StateFlow<ProfileDetailUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            repository.getProfile().fold(
                onSuccess = { profile ->
                    _uiState.update {
                        it.copy(
                            userProfile = profile,
                            isLoading = false,
                            editRealName = profile.realName,
                            editPhone = profile.phone,
                            editRegion = profile.region
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load profile"
                        )
                    }
                }
            )
        }
    }

    fun startEditing() {
        val currentProfile = _uiState.value.userProfile ?: return
        _uiState.update {
            it.copy(
                isEditing = true,
                editRealName = currentProfile.realName,
                editPhone = currentProfile.phone,
                editRegion = currentProfile.region,
                editErrorMessage = null
            )
        }
    }

    fun cancelEditing() {
        _uiState.update {
            it.copy(
                isEditing = false,
                editErrorMessage = null
            )
        }
    }

    fun onEditRealNameChange(newValue: String) {
        _uiState.update { it.copy(editRealName = newValue, editErrorMessage = null) }
    }

    fun onEditPhoneChange(newValue: String) {
        _uiState.update { it.copy(editPhone = newValue, editErrorMessage = null) }
    }

    fun onEditRegionChange(newValue: String) {
        _uiState.update { it.copy(editRegion = newValue, editErrorMessage = null) }
    }

    fun saveProfile() {
        val state = _uiState.value
        if (state.isLoading) return

        if (state.editRealName.isBlank() || state.editPhone.isBlank() || state.editRegion.isBlank()) {
            _uiState.update { it.copy(editErrorMessage = "All fields are required") }
            return
        }

        _uiState.update { it.copy(isLoading = true, editErrorMessage = null) }
        viewModelScope.launch {
            repository.updateProfile(
                realName = state.editRealName,
                phone = state.editPhone,
                region = state.editRegion
            ).fold(
                onSuccess = { profile ->
                    _uiState.update {
                        it.copy(
                            userProfile = profile,
                            isEditing = false,
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            editErrorMessage = error.message ?: "Failed to update profile"
                        )
                    }
                }
            )
        }
    }

    fun startChangingPassword() {
        _uiState.update {
            it.copy(
                isChangingPassword = true,
                oldPasswordText = "",
                newPasswordText = "",
                confirmPasswordText = "",
                oldPasswordErrorMessage = null,
                newPasswordErrorMessage = null,
                confirmPasswordErrorMessage = null,
                passwordStrength = 0,
                passwordStrengthLabel = "Too weak",
                changePasswordSuccess = false
            )
        }
    }

    fun cancelChangingPassword() {
        _uiState.update {
            it.copy(
                isChangingPassword = false,
                oldPasswordText = "",
                newPasswordText = "",
                confirmPasswordText = "",
                oldPasswordErrorMessage = null,
                newPasswordErrorMessage = null,
                confirmPasswordErrorMessage = null
            )
        }
    }

    fun onOldPasswordChange(newValue: String) {
        _uiState.update { it.copy(oldPasswordText = newValue, oldPasswordErrorMessage = null) }
    }

    fun onNewPasswordChange(newValue: String) {
        val (strength, label) = evaluatePasswordStrength(newValue)
        _uiState.update {
            it.copy(
                newPasswordText = newValue,
                newPasswordErrorMessage = null,
                passwordStrength = strength,
                passwordStrengthLabel = label
            )
        }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPasswordText = newValue, confirmPasswordErrorMessage = null) }
    }

    fun onToggleOldPasswordVisibility() {
        _uiState.update { it.copy(showOldPassword = !it.showOldPassword) }
    }

    fun onToggleNewPasswordVisibility() {
        _uiState.update { it.copy(showNewPassword = !it.showNewPassword) }
    }

    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(showConfirmPassword = !it.showConfirmPassword) }
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

    fun changePassword() {
        val state = _uiState.value
        if (state.isLoading) return

        var hasError = false
        if (state.oldPasswordText.isEmpty()) {
            _uiState.update { it.copy(oldPasswordErrorMessage = "Current password is required") }
            hasError = true
        }
        if (state.newPasswordText.isEmpty()) {
            _uiState.update { it.copy(newPasswordErrorMessage = "New password is required") }
            hasError = true
        } else if (state.newPasswordText.length < 8) {
            _uiState.update { it.copy(newPasswordErrorMessage = "Password must be at least 8 characters") }
            hasError = true
        }
        if (state.confirmPasswordText.isEmpty()) {
            _uiState.update { it.copy(confirmPasswordErrorMessage = "Confirm password is required") }
            hasError = true
        } else if (state.confirmPasswordText != state.newPasswordText) {
            _uiState.update { it.copy(confirmPasswordErrorMessage = "Passwords do not match") }
            hasError = true
        }

        if (hasError) return

        _uiState.update { it.copy(isLoading = true, oldPasswordErrorMessage = null, newPasswordErrorMessage = null, confirmPasswordErrorMessage = null) }
        viewModelScope.launch {
            repository.changePassword(state.oldPasswordText, state.newPasswordText).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            changePasswordSuccess = true,
                            isChangingPassword = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            oldPasswordErrorMessage = error.message ?: "Failed to change password"
                        )
                    }
                }
            )
        }
    }

    fun resetSuccessState() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun resetChangePasswordSuccess() {
        _uiState.update { it.copy(changePasswordSuccess = false) }
    }
}
