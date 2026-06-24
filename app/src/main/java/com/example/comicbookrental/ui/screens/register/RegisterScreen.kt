package com.example.comicbookrental.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.ui.components.AuthTopHeader
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.components.FacebookLoginButton
import com.example.comicbookrental.ui.components.GoogleLoginButton
import com.example.comicbookrental.ui.components.BrutalistButton
import com.example.comicbookrental.ui.components.ComicCard
import com.example.comicbookrental.ui.components.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.Success
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
)
{
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess)
        {
            onRegisterSuccess()
            viewModel.resetSuccessState()
        }
    }

    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .comicHalftoneBackground(dotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = Dimens.Spacing.SectionSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopHeader(onCloseClick = onLoginClick)

        HorizontalDivider(
            color = ink,
            thickness = Dimens.Border.Standard,
            modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin)
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Spacing.ScreenPadding)
        ) {
            Text(
                text = "JOIN THE",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.offset(y = 12.dp)
            )

            Box(
                modifier = Modifier.padding(
                    end = Dimens.Elevation.Resting,
                    bottom = Dimens.Elevation.Resting
                )
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .offset(x = Dimens.Elevation.Resting, y = Dimens.Elevation.Resting)
                        .background(ink, RoundedCornerShape(Dimens.Radius.Sm))
                )
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(Dimens.Radius.Sm)
                        )
                        .border(Dimens.Border.Standard, ink, RoundedCornerShape(Dimens.Radius.Sm))
                        .padding(horizontal = Dimens.Spacing.Gutter, vertical = Dimens.GridUnit)
                ) {
                    Text(
                        text = "SQUAD",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

            ComicCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimens.Radius.Sm),
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = ink,
                borderWidth = Dimens.Border.Standard,
                shadowOffset = Dimens.Elevation.Resting,
                contentPadding = PaddingValues(Dimens.Spacing.AuthPadding)
            ) {
                if (state.errorMessage != null)
                {
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
                    value = state.fullName,
                    onValueChange = viewModel::onNameChange,
                    label = "FULL NAME",
                    placeholder = "WADE WILSON",
                    leadingIcon = Icons.Outlined.Person
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

                BrutalistTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    label = "EMAIL ADDRESS",
                    placeholder = "HERO@PANELRUSH.COM",
                    leadingIcon = Icons.Outlined.Email
                )

                if (state.emailError != null)
                {
                    Text(
                        text = state.emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

                BrutalistTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = "SECRET IDENTITY (PASSWORD)",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = !state.isViewPassword,
                    trailingContent = {
                        if (state.isViewPassword)
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

                if (state.passwordError != null)
                {
                    Text(
                        text = state.passwordError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

                BrutalistTextField(
                    value = state.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    label = "CONFIRM PASSWORD",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Outlined.Security,
                    isPassword = !state.isViewConfirmPassword,
                    trailingContent = {
                        if (state.isViewPassword)
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

                if (state.confirmPasswordError != null)
                {
                    Text(
                        text = state.confirmPasswordError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = state.isAgreed,
                        onCheckedChange = viewModel::onAcceptTermsChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = ink,
                            checkmarkColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.size(Dimens.Icon.Medium)
                    )

                    Spacer(modifier = Modifier.width(Dimens.Spacing.ContentSpacing))

                    Text(
                        text = buildAnnotatedString {
                            append("I AGREE TO THE ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.secondary,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("CRITICAL ALLIANCE TERMS")
                            }
                            append(" AND PRIVACY PROTOCOLS.")
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                if (state.isLoading)
                {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(48.dp),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))
                }

                BrutalistButton(
                    text = "SIGN UP",
                    onClick = { viewModel.onSignUp() },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                HorizontalDivider(
                    thickness = Dimens.Border.Hairline,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "ALREADY A MEMBER? ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "LOGIN HERE",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { onLoginClick() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter)
            ) {
                GoogleLoginButton(
                    onClick = { viewModel.onOAuthLogin() },
                    modifier = Modifier.weight(1f)
                )
                FacebookLoginButton(
                    onClick = { viewModel.onOAuthLogin() },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}