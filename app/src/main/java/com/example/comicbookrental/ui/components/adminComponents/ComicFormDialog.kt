package com.example.comicbookrental.ui.components.adminComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.screens.admin.manage_comics.ComicFormState
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.Error

@Composable
fun ComicFormDialog(
    form: ComicFormState,
    onChange: (ComicFormState) -> Unit,
    onSave: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (form.isEditing) "EDIT COMIC" else "ADD COMIC",
                fontFamily = Anton,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd)
            ) {
                BrutalistTextField(
                    value = form.title,
                    onValueChange = { onChange(form.copy(title = it)) },
                    label = "Title",
                    placeholder = "Solo Leveling",
                    leadingIcon = Icons.Default.Title
                )
                BrutalistTextField(
                    value = form.author,
                    onValueChange = { onChange(form.copy(author = it)) },
                    label = "Author",
                    placeholder = "Chugong",
                    leadingIcon = Icons.Default.Person
                )
                BrutalistTextField(
                    value = form.genre,
                    onValueChange = { onChange(form.copy(genre = it)) },
                    label = "Genres (comma-separated)",
                    placeholder = "Action, Comedy",
                    leadingIcon = Icons.Default.Category
                )
                BrutalistTextField(
                    value = form.publisher,
                    onValueChange = { onChange(form.copy(publisher = it)) },
                    label = "Publisher",
                    placeholder = "D&C Media",
                    leadingIcon = Icons.Default.Business
                )
                BrutalistTextField(
                    value = form.rentalPrice,
                    onValueChange = { onChange(form.copy(rentalPrice = it)) },
                    label = "Rental price / day",
                    placeholder = "2.50",
                    leadingIcon = Icons.Default.AttachMoney
                )
                BrutalistTextField(
                    value = form.latestChapter,
                    onValueChange = { onChange(form.copy(latestChapter = it)) },
                    label = "Latest chapter",
                    placeholder = "1",
                    leadingIcon = Icons.Default.MenuBook
                )
                BrutalistTextField(
                    value = form.releaseDate,
                    onValueChange = { onChange(form.copy(releaseDate = it)) },
                    label = "Release date",
                    placeholder = "2024-01-15",
                    leadingIcon = Icons.Default.DateRange
                )
                BrutalistTextField(
                    value = form.coverImageUrl,
                    onValueChange = { onChange(form.copy(coverImageUrl = it)) },
                    label = "Cover image (URL)",
                    placeholder = "https://...",
                    leadingIcon = Icons.Default.Image
                )
                BrutalistTextField(
                    value = form.description,
                    onValueChange = { onChange(form.copy(description = it)) },
                    label = "Description",
                    placeholder = "Short synopsis...",
                    leadingIcon = Icons.Default.Notes
                )
                if (form.error.isNotEmpty()) {
                    Text(
                        text = form.error,
                        color = Error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {
            BrutalistButton(text = "Save", onClick = onSave)
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
