package com.example.comicbookrental.ui.components.adminComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun UserActionDialog(
    email: String,
    ban: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (ban) "BAN USER" else "UNBAN USER",
                fontFamily = Anton,
                fontWeight = FontWeight.Normal,
                color = if (ban) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = if (ban)
                    "Ban \"$email\"? This user won't be able to sign in until unbanned."
                else
                    "Unban \"$email\"? This user will be able to sign in again.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            BrutalistButton(text = if (ban) "Ban" else "Unban", onClick = onConfirm)
        },
        dismissButton = {
            ComicButton(
                text = "Cancel",
                onClick = onDismiss,
                variant = ComicButtonVariant.Secondary
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(Dimens.Radius.Card)
    )
}
