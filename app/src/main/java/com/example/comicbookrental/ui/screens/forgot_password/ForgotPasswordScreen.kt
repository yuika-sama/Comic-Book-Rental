package com.example.comicbookrental.ui.screens.forgot_password

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.ui.components.AuthTopHeader
import com.example.comicbookrental.ui.components.BrutalistButton
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.components.ComicCard
import com.example.comicbookrental.ui.components.LoopText
import com.example.comicbookrental.ui.components.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun ForgotPasswordScreen(
    onBackToLoginClick: () -> Unit = {},
    onSendOtpClick: (String) -> Unit = {},
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Sm)

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onSendOtpClick(state.email)
            viewModel.resetSuccessState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .comicHalftoneBackground(dotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AuthTopHeader(onCloseClick = onBackToLoginClick)

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Spacing.Margin)
                .weight(1f)
        ){
            ComicCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.Sizes.LargeButtonHeight / 2),
                shape = shape,
                containerColor = MaterialTheme.colorScheme.background,
                borderColor = ink,
                borderWidth = Dimens.Border.Standard,
                shadowOffset = Dimens.Elevation.Resting,
                contentPadding = PaddingValues(
                    start = Dimens.Spacing.AuthPadding,
                    end = Dimens.Spacing.AuthPadding,
                    top = Dimens.Spacing.SectionSpacing + Dimens.Spacing.ContentSpacing,
                    bottom = Dimens.Spacing.AuthPadding
                )
            ) {
                Text(
                   text = "RECOVER YOUR ACCOUNT",
                   style = MaterialTheme.typography.headlineMedium,
                   color = MaterialTheme.colorScheme.onBackground,
                   textAlign = TextAlign.Center,
                   modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(Dimens.Spacing.ListItemSpacing))
                
                Text(
                    text = buildAnnotatedString {
                        append("Lost your way in the Multiverse? Enter your email and we'll send an ")
                        withStyle(
                            style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
                        ){
                            append("RESCUE OTP ")
                        }
                        append("to your inbox.")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
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
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    label = "SECRET IDENTITY (EMAIL)",
                    placeholder = "superhero@panelrush.com",
                    leadingIcon = Icons.Outlined.Email
                )

                if (state.emailErrorMessage != null) {
                    Text(
                        text = state.emailErrorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Dimens.Spacing.ListItemSpacing)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                if (state.isLoading) {
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
                    text = if (state.isLoading) "SENDING..." else "SEND OTP",
                    onClick = { viewModel.sendOtp() },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onBackToLoginClick() }
                ) {
                    Text(
                        text = "← ", 
                        color = MaterialTheme.colorScheme.secondary, 
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "BACK TO LOGIN",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Box(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                Box(
                    modifier = Modifier
                        .size(Dimens.Sizes.LargeButtonHeight)
                        .offset(x = Dimens.Elevation.Resting, y = Dimens.Elevation.Resting)
                        .background(ink, shape)
                )

                Box(
                    modifier = Modifier
                        .size(Dimens.Sizes.LargeButtonHeight)
                        .background(MaterialTheme.colorScheme.primary, shape)
                        .border(Dimens.Border.Standard, ink, shape),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Outlined.Replay,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(Dimens.Icon.Medium)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        LoopText(value = "THE FIGHT • NEVER LOSE YOUR PROGRESS • SECURE YOUR COLLECTION • ")
    }
}