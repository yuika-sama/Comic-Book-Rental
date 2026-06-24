package com.example.comicbookrental.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtonsSection(
    isEditing: Boolean = false,
    onEditClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        if (isEditing) {
            BrutalistButton(
                text = "SAVE CHANGES",
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )

            ComicButton(
                text = "CANCEL",
                onClick = onCancelClick,
                variant = ComicButtonVariant.Secondary,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            BrutalistButton(
                text = "EDIT PROFILE",
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth()
            )

            ComicButton(
                text = "CHANGE PASSWORD",
                onClick = onChangePasswordClick,
                variant = ComicButtonVariant.Secondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}