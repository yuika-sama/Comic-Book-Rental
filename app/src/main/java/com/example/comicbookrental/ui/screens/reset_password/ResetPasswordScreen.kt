package com.example.comicbookrental.ui.screens.reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.components.AuthIconBox
import com.example.comicbookrental.ui.components.AuthTopHeader
import com.example.comicbookrental.ui.components.BrutalistButton
import com.example.comicbookrental.ui.components.BrutalistTextField
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.components.ComicCard
import com.example.comicbookrental.ui.components.PasswordStrengthEvaluator
import com.example.comicbookrental.ui.components.SecurityAlert
import com.example.comicbookrental.ui.components.comicHalftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun ResetPasswordScreen(
    onPasswordResetSuccess: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    val ink = MaterialTheme.extendedColors.ink

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

                BrutalistTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = "NEW PASSWORD",
                    placeholder = "Enter your new password",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true
                )
                
                Spacer(modifier = Modifier.height(Dimens.Spacing.ListItemSpacing))
                
                PasswordStrengthEvaluator(strength = 3, label = "Almost invincible")

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                BrutalistTextField(
                    value = confirmNewPassword,
                    onValueChange = { confirmNewPassword = it },
                    label = "CONFIRM NEW PASSWORD",
                    placeholder = "Enter your confirm password",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                SecurityAlert(
                    message = "Updating your password will sign you out of all other active comic sessions on other devices."
                )

                Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

                BrutalistButton(
                    text = "UPDATE PASSWORD",
                    onClick = onPasswordResetSuccess,
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
            onPasswordResetSuccess = {},
            onCancelClick = {}
        )
    }
}