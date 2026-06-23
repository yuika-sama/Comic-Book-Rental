package com.example.comicbookrental.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Dimens
import androidx.compose.ui.focus.FocusRequester
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun OtpCard(
    otpValues: List<String>,
    onOtpChange: (Int, String) -> Unit,
    focusRequesters: List<FocusRequester>,
    onVerifyClick: () -> Unit = {},
    onResendClick: () -> Unit = {},
    isLoading: Boolean = false,
    resendCooldownSeconds: Int = 0,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
){
    ComicCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.Radius.Sm),
        containerColor = MaterialTheme.colorScheme.background,
        borderColor = MaterialTheme.extendedColors.ink,
        borderWidth = Dimens.Border.Standard,
        shadowOffset = Dimens.Elevation.Resting,
        contentPadding = PaddingValues(Dimens.Spacing.AuthPadding)
    ) {
        Text(
            text = "VERIFY YOUR EMAIL",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.StackSm))

        Text(
            text = "We've sent an OTP to your email. Input it below",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.Spacing.ListItemSpacing)
            )
        }

        OtpInputRow(
            otpCount = 5,
            otpValues = otpValues,
            onOtpChange = onOtpChange,
            focusRequesters = focusRequesters
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        BrutalistButton(
            text = if (isLoading) "VERIFYING..." else "VERIFY",
            onClick = { if (!isLoading) onVerifyClick() },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.StackMd))

        Row {
            Text(
                text = "Didn't get it? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "RESEND CODE",
                style = MaterialTheme.typography.labelLarge,
                color = if (resendCooldownSeconds > 0 || isLoading) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(enabled = resendCooldownSeconds == 0 && !isLoading) { onResendClick() }
            )
        }

        if (resendCooldownSeconds > 0) {
            Spacer(modifier = Modifier.height(Dimens.Spacing.StackSm))

            Text(
                text = "Wait $resendCooldownSeconds seconds before resending.",
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}