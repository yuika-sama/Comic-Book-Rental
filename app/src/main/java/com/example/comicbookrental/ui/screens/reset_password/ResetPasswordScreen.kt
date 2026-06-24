package com.example.comicbookrental.ui.screens.reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.ui.components.authComponents.AuthIconBox
import com.example.comicbookrental.ui.components.authComponents.AuthTopHeader
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.components.authComponents.ComicCard
import com.example.comicbookrental.ui.components.authComponents.PasswordStrengthEvaluator
import com.example.comicbookrental.ui.components.authComponents.SecurityAlert
import com.example.comicbookrental.ui.components.commonComponents.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun ResetPasswordScreen(
    email: String,
    onPasswordResetSuccess: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val ink = MaterialTheme.extendedColors.ink

    LaunchedEffect(email) {
        viewModel.initEmail(email)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onPasswordResetSuccess()
            viewModel.resetSuccessState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .comicHalftoneBackground(dotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopHeader(onCloseClick = onCancelClick)

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        ComicCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Spacing.ScreenPadding),
            shape = RoundedCornerShape(Dimens.Radius.Sm),
            containerColor = MaterialTheme.colorScheme.background,
            borderColor = ink,
            borderWidth = Dimens.Border.Standard,
            shadowOffset = Dimens.Elevation.Resting,
            contentPadding = PaddingValues(Dimens.Spacing.AuthPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthIconBox(icon = Icons.Default.Lock)

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                Text(
                    text = "RESET PASSWORD",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(Dimens.Spacing.ListItemSpacing))
                
                Text(
                    text = "Lock your vault with a high-security access key.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimens.Spacing.ListItemSpacing)
                    )
                }

                BrutalistTextField(
                    value = state.newPassword,
                    onValueChange = viewModel::onNewPasswordChange,
                    label = "NEW PASSWORD",
                    placeholder = "Enter your new password",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = !state.isPasswordShow,
                    trailingContent = {
                        if (state.isPasswordShow)
                        {
                            Icon(
                                imageVector = Icons.Outlined.VisibilityOff,
                                contentDescription = "Hide Password",
                                modifier = Modifier
                                    .size(Dimens.Icon.Medium)
                                    .clickable {
                                        viewModel.onTogglePasswordVisibility()
                                    },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        else
                        {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = "Show Password",
                                modifier = Modifier
                                    .size(Dimens.Icon.Medium)
                                    .clickable {
                                        viewModel.onTogglePasswordVisibility()
                                    },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )

                if (state.newPasswordErrorMessage != null) {
                    Text(
                        text = state.newPasswordErrorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.Spacing.ListItemSpacing)
                    )
                }
                
                Spacer(modifier = Modifier.height(Dimens.Spacing.ListItemSpacing))
                
                PasswordStrengthEvaluator(strength = state.passwordStrength, label = state.passwordStrengthLabel)

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                BrutalistTextField(
                    value = state.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    label = "CONFIRM NEW PASSWORD",
                    placeholder = "Enter your confirm password",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = !state.isConfirmPasswordShow,
                    trailingContent = {
                        if (state.isConfirmPasswordShow)
                        {
                            Icon(
                                imageVector = Icons.Outlined.VisibilityOff,
                                contentDescription = "Hide Password",
                                modifier = Modifier
                                    .size(Dimens.Icon.Medium)
                                    .clickable {
                                        viewModel.onToggleConfirmPasswordVisibility()
                                    },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        else
                        {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = "Show Password",
                                modifier = Modifier
                                    .size(Dimens.Icon.Medium)
                                    .clickable {
                                        viewModel.onToggleConfirmPasswordVisibility()
                                    },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )

                if (state.confirmPasswordErrorMessage != null) {
                    Text(
                        text = state.confirmPasswordErrorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                BrutalistButton(
                    text = if (state.isLoading) "UPDATING..." else "UPDATE PASSWORD",
                    onClick = { viewModel.resetPassword() },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

                ComicButton(
                    text = "CANCEL RECOVERY",
                    onClick = onCancelClick,
                    variant = ComicButtonVariant.Secondary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun ResetPasswordPreview() {
    ComicBookRentalTheme {
        ResetPasswordScreen(
            email = "comic.rent@gmail.com",
            onPasswordResetSuccess = {},
            onCancelClick = {}
        )
    }
}