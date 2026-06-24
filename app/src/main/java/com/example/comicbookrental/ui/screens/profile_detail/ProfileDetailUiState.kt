package com.example.comicbookrental.ui.screens.profile_detail

import com.example.comicbookrental.data.entities.UserProfile
import com.example.comicbookrental.data.models.User

data class ProfileDetailUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,

    val isEditing: Boolean = false,
    val editRealName: String = "",
    val editPhone: String = "",
    val editRegion: String = "",
    val editErrorMessage: String? = null,

    val isChangingPassword: Boolean = false,
    val oldPasswordText: String = "",
    val newPasswordText: String = "",
    val confirmPasswordText: String = "",
    val oldPasswordErrorMessage: String? = null,
    val newPasswordErrorMessage: String? = null,
    val confirmPasswordErrorMessage: String? = null,
    val passwordStrength: Int = 0,
    val passwordStrengthLabel: String = "Too weak",
    val showOldPassword: Boolean = false,
    val showNewPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val changePasswordSuccess: Boolean = false,

    val editHeroName: String = "",
    val isUpdatingAvatar: Boolean = false,
    val editAvatarUrl: String = ""

)
