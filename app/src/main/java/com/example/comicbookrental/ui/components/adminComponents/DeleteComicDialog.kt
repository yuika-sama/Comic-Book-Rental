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
fun DeleteComicDialog(
    comicTitle: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "DELETE COMIC",
                fontFamily = Anton,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(
                text = "Delete \"$comicTitle\" from the catalog? This action can't be undone.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            BrutalistButton(text = "Delete", onClick = onConfirm)
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
