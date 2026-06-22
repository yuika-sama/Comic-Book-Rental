package com.example.comicbookrental.ui.components

import android.content.res.Resources
import androidx.compose.foundation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.RentalEntity
import com.example.comicbookrental.ui.theme.*
import com.example.comicbookrental.ui.theme.Surface
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RentalCard(
    rental: RentalEntity
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Comic Cover
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "📖",
                    style = MaterialTheme.typography.headlineLarge
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            // Rental Info
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Comic #${rental.comicId}",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                InfoRow(
                    label = "Rental",
                    value = rental.rentalDate.toFormattedDate()
                )

                InfoRow(
                    label = "Due",
                    value = rental.dueDate.toFormattedDate()
                )

                InfoRow(
                    label = "User",
                    value = rental.userId.toString()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                StatusChip(
                    status = rental.status
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier.padding(vertical = 2.dp)
    ) {

        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun StatusChip(
    status: String
) {

    val backgroundColor =
        when (status) {
            "ACTIVE" -> Success
            "EXPIRED" -> Error
            else -> MaterialTheme.colorScheme.surfaceVariant
        }

    val textColor =
        when (status) {
            "ACTIVE" -> Color.White
            "EXPIRED" -> Color.White
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50)
    ) {

        Text(
            text = status,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            color = textColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

private fun Long.toFormattedDate(): String {

    return SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).format(
        Date(this)
    )
}
