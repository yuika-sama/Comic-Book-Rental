package com.example.comicbookrental.ui.components.rentalComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.RentalStatus
import com.example.comicbookrental.ui.theme.Anton

@Composable
fun RentalStatusChip(
    status: RentalStatus
) {
    val isActive = status == RentalStatus.ACTIVE

    val containerColor = if (isActive) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }

    Surface(
        color = containerColor,
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = Anton
            ),
            color = contentColor
        )
    }
}