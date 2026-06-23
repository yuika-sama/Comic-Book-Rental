package com.example.comicbookrental.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.AuthTopHeader
import com.example.comicbookrental.ui.components.LoopText
import com.example.comicbookrental.ui.components.OtpCard
import com.example.comicbookrental.ui.components.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun VerifyOtpScreen(
    onVerifySuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
){
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
            onVerifyClick = onVerifySuccess,
            onResendClick = {}
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
            onVerifySuccess = {},
            onBackClick = {}
        )
    }
}