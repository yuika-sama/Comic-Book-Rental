package com.example.comicbookrental.ui.components.profileComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.theme.Error

@Composable
fun UpdateProfileDialog(
    onCancelEditing: () -> Unit,
    editHeroName: String,
    onEditHeroName: (String) -> Unit,
    editRealName: String,
    onEditRealName: (String) -> Unit,
    editPhone: String,
    onEditPhone: (String) -> Unit,
    editRegion: String,
    onEditRegion: (String) -> Unit,
    errorMessage: String = "",
    onSaveProfile: () -> Unit,
)
{
    AlertDialog(
        onDismissRequest = onCancelEditing,
        title = { Text("UPDATE PROFILE", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                BrutalistTextField(
                    value = editHeroName,
                    onValueChange = onEditHeroName,
                    label = "HERO NAME",
                    placeholder = "Your display name",
                    leadingIcon = Icons.Default.TextFormat
                )
                BrutalistTextField(
                    value = editRealName,
                    onValueChange = onEditRealName,
                    label = "REAL NAME",
                    placeholder = "Your real name",
                    leadingIcon = Icons.Default.Person
                )
                BrutalistTextField(
                    value = editPhone,
                    onValueChange = onEditPhone,
                    label = "PHONE NUMBER",
                    placeholder = "+84 (09) 87 654 321",
                    leadingIcon = Icons.Default.Phone
                )
                BrutalistTextField(
                    value = editRegion,
                    onValueChange = onEditRegion,
                    label = "REGION",
                    placeholder = "Your region",
                    leadingIcon = Icons.Default.TextFormat
                )
                if (errorMessage != "")
                {
                    Text(errorMessage, color = Error, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            BrutalistButton(text = "SAVE", onClick = onSaveProfile)
        },
        dismissButton = {
            ComicButton(
                text = "CANCEL",
                onClick = onCancelEditing,
                variant = ComicButtonVariant.Secondary
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp)
    )
}