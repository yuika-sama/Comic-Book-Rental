package com.example.comicbookrental.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
//import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.components.authComponents.FacebookLoginButton
import com.example.comicbookrental.ui.components.authComponents.GoogleLoginButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.authComponents.ComicCard
import com.example.comicbookrental.ui.components.authComponents.LoginSectionDivider
import com.example.comicbookrental.ui.components.authComponents.LoginTitleSection
import com.example.comicbookrental.ui.components.authComponents.RememberMeCheckBox
import com.example.comicbookrental.ui.components.commonComponents.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.AuthTitleSize
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onNavigateToVerify: (String) -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
)
{
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess)
        {
            onLoginSuccess()
            viewModel.resetSuccessState()
        }
    }

    LaunchedEffect(state.requiresVerification) {
        if (state.requiresVerification){
            onNavigateToVerify(state.email)
            viewModel.resetVerificationState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .comicHalftoneBackground(dotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .padding(Dimens.Spacing.ScreenPadding), contentAlignment = Alignment.Center
    ) {
        ComicCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.Radius.Sm),
            containerColor = MaterialTheme.colorScheme.background,
            borderColor = MaterialTheme.extendedColors.ink,
            borderWidth = Dimens.Border.Standard,
            shadowOffset = Dimens.Elevation.Resting,
            contentPadding = PaddingValues(Dimens.Spacing.AuthPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LoginTitleSection()

                Spacer(modifier = Modifier.height(Dimens.Sizes.ButtonHeight))

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
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    label = "EMAIL ADDRESS",
                    placeholder = "comic.rent@gmail.com",
                    leadingIcon = Icons.Outlined.Email
                )

                if (state.emailErrorMessage != null)
                {
                    Text(
                        text = state.emailErrorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                BrutalistTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = "PASSWORD",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    trailingContent = {
                        Text(
                            text = "FORGOT?",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { onForgotPasswordClick() })
                    })

                if (state.passwordErrorMessage != null)
                {
                    Text(
                        text = state.passwordErrorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.ContentSpacing))

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
                    text = if (state.isLoading) "AUTHENTICATING..." else "LOGIN",
                    onClick = { viewModel.login() },
                    modifier = Modifier.fillMaxWidth()
                )

                RememberMeCheckBox(
                    value = state.rememberMe,
                    onToggleCheckBox = viewModel::onRememberMeChange
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                LoginSectionDivider()

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter)
                ) {
                    GoogleLoginButton(
                        onClick = { viewModel.onOAuthLogin() }, modifier = Modifier.weight(1f)
                    )
                    FacebookLoginButton(
                        onClick = { viewModel.onOAuthLogin() }, modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                Row(modifier = Modifier.padding(bottom = Dimens.Spacing.ListItemSpacing)) {
                    Text(
                        text = "New to the collection? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "JOIN TODAY",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.clickable { onRegisterClick() })
                }
            }
        }
    }
}