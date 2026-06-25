package com.example.comicbookrental.ui.screens.profile_detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.ui.components.profileComponents.ActionButtonsSection
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.profileComponents.HeroIdentityCard
import com.example.comicbookrental.ui.components.authComponents.PasswordStrengthEvaluator
import com.example.comicbookrental.ui.components.profileComponents.ProfileInformationCard
import com.example.comicbookrental.ui.components.profileComponents.UpdateAvatarDialog
import com.example.comicbookrental.ui.components.profileComponents.UpdateProfileDialog

@Composable
fun ProfileDetailScreen(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    viewModel: ProfileDetailViewModel = hiltViewModel()
)
{
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess)
        {
            Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            viewModel.resetSuccessState()
        }
    }

    LaunchedEffect(state.changePasswordSuccess) {
        if (state.changePasswordSuccess)
        {
            Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()
            viewModel.resetChangePasswordSuccess()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        com.example.comicbookrental.ui.components.commonComponents.SecondaryTopBar(
            title = "PROFILE EDIT",
            onBackClick = onBackClick,
            onCartClick = onCartClick,
            isShowHeart = false
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            val profile = state.user
            println(profile?.isEmailVerified)
            HeroIdentityCard(
                imageUrl = profile?.avatarUrl ?: null,
                heroName = profile?.heroName ?: "LOADING...",
                rank = profile?.rank ?: "HEROIC",
                email = profile?.email ?: "loading...",
                isVerified = profile?.isEmailVerified ?: false,
                onAvatarClick = {viewModel.startUpdatingAvatar()}
            )

            ProfileInformationCard(
                isEditing = state.isEditing,
                realName = if (state.isEditing) state.editRealName else (profile?.realName ?: ""),
                phone = if (state.isEditing) state.editPhone else (profile?.phone ?: ""),
                region = if (state.isEditing) state.editRegion else (profile?.region ?: ""),
                onRealNameChange = viewModel::onEditRealNameChange,
                onPhoneChange = viewModel::onEditPhoneChange,
                onRegionChange = viewModel::onEditRegionChange
            )

            val editError = state.editErrorMessage
            if (editError != null)
            {
                Text(
                    text = editError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            ActionButtonsSection(
                isEditing = state.isEditing,
                onEditClick = { viewModel.startEditing() },
                onSaveClick = { viewModel.saveProfile() },
                onCancelClick = { viewModel.cancelEditing() },
                onChangePasswordClick = { viewModel.startChangingPassword() }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (state.isChangingPassword)
    {
        AlertDialog(
            onDismissRequest = { viewModel.cancelChangingPassword() },
            title = { Text("CHANGE ACCESS KEY", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BrutalistTextField(
                        value = state.oldPasswordText,
                        onValueChange = viewModel::onOldPasswordChange,
                        label = "CURRENT PASSWORD",
                        placeholder = "Enter current password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = !state.showOldPassword,
                        trailingContent = {
                            Icon(
                                imageVector = if (state.showOldPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = "Toggle Visibility",
                                modifier = Modifier.clickable { viewModel.onToggleOldPasswordVisibility() }
                            )
                        }
                    )
                    if (state.oldPasswordErrorMessage != null)
                    {
                        Text(
                            state.oldPasswordErrorMessage!!,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }

                    BrutalistTextField(
                        value = state.newPasswordText,
                        onValueChange = viewModel::onNewPasswordChange,
                        label = "NEW PASSWORD",
                        placeholder = "Enter new password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = !state.showNewPassword,
                        trailingContent = {
                            Icon(
                                imageVector = if (state.showNewPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = "Toggle Visibility",
                                modifier = Modifier.clickable { viewModel.onToggleNewPasswordVisibility() }
                            )
                        }
                    )
                    if (state.newPasswordErrorMessage != null)
                    {
                        Text(
                            state.newPasswordErrorMessage!!,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }

                    PasswordStrengthEvaluator(
                        strength = state.passwordStrength,
                        label = state.passwordStrengthLabel
                    )

                    BrutalistTextField(
                        value = state.confirmPasswordText,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        label = "CONFIRM NEW PASSWORD",
                        placeholder = "Confirm new password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = !state.showConfirmPassword,
                        trailingContent = {
                            Icon(
                                imageVector = if (state.showConfirmPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = "Toggle Visibility",
                                modifier = Modifier.clickable { viewModel.onToggleConfirmPasswordVisibility() }
                            )
                        }
                    )
                    if (state.confirmPasswordErrorMessage != null)
                    {
                        Text(
                            state.confirmPasswordErrorMessage!!,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            },
            confirmButton = {
                BrutalistButton(
                    text = if (state.isLoading) "UPDATING..." else "CONFIRM",
                    onClick = { viewModel.changePassword() }
                )
            },
            dismissButton = {
                ComicButton(
                    text = "CANCEL",
                    onClick = { viewModel.cancelChangingPassword() },
                    variant = ComicButtonVariant.Secondary
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(8.dp)
        )
    }

    if (state.isEditing){
        UpdateProfileDialog(
            onCancelEditing = {viewModel.cancelEditing()},
            editHeroName = state.editHeroName,
            onEditHeroName = {viewModel.onEditHeroNameChange(it)},
            editRealName = state.editRealName,
            onEditRealName = {viewModel.onEditRealNameChange(it)},
            editPhone = state.editPhone,
            onEditPhone = { viewModel.onEditPhoneChange(it) },
            editRegion = state.editRegion,
            onEditRegion = { viewModel.onEditRegionChange(it) },
            errorMessage = state.editErrorMessage ?: "",
            onSaveProfile = { viewModel.saveProfile() }
        )
    }

    if (state.isUpdatingAvatar)
    {
        UpdateAvatarDialog(
            onUploadLocalAvatar = viewModel::uploadLocalImage,
            onUploadUrlAvatar = viewModel::onEditAvatarUrlChange,
            onCancelUpdateAvatar = viewModel::cancelUpdatingAvatar,
            editAvatarUrl = state.editAvatarUrl,
            onSaveAvatar = { viewModel.saveAvatar() }
        )
    }
}