package com.example.comicbookrental.ui.screens.verify_otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.comicbookrental.ui.components.authComponents.AuthTopHeader
import com.example.comicbookrental.ui.components.commonComponents.LoopText
import com.example.comicbookrental.ui.components.authComponents.OtpCard
import com.example.comicbookrental.ui.components.commonComponents.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun VerifyOtpScreen(
    email: String,
    onVerifySuccess: (isAdmin: Boolean) -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: VerifyOtpViewModel = hiltViewModel()
){
    val state by viewModel.uiState.collectAsState()
    val focusRequesters = remember { List(state.otpLength) { FocusRequester() } }

    LaunchedEffect(email) {
        viewModel.initEmail(email)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onVerifySuccess(state.isAdmin)
            viewModel.resetSuccessState()
        }
    }

    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage != null && state.otpValues.all { it.isEmpty() }) {
            focusRequesters[0].requestFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .comicHalftoneBackground(dotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            .padding(Dimens.Spacing.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AuthTopHeader(onCloseClick = onBackClick)

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        OtpCard(
            otpValues = state.otpValues,
            onOtpChange = viewModel::onOtpChange,
            focusRequesters = focusRequesters,
            onVerifyClick = viewModel::verifyOtp,
            onResendClick = viewModel::resendOtp,
            isLoading = state.isLoading || state.isResending,
            resendCooldownSeconds = state.resendCooldown,
            errorMessage = state.errorMessage,
            otpLength = state.otpLength
        )

        Spacer(modifier = Modifier.weight(1f))

        LoopText(value = "THE FIGHT • NEVER LOSE YOUR PROGRESS • SECURE YOUR COLLECTION • ")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun VerifyOtpPreview() {
    ComicBookRentalTheme {
        VerifyOtpScreen(
            email = "comic.rent@gmail.com",
            onVerifySuccess = {},
            onBackClick = {}
        )
    }
}