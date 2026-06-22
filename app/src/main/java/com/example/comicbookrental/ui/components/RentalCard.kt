package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.models.Rental
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RentalCard(
    rental: Rental,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("📖", style = MaterialTheme.typography.headlineLarge)
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = rental.comicTitle,
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Rental: ${rental.rentalDate.toFormattedDate()}")
                Text("Due: ${rental.dueDate.toFormattedDate()}")
                Text("Status: ${rental.status.name}")

                Spacer(Modifier.height(8.dp))

                TextButton(onClick = onDeleteClick) {
                    Text("Remove")
                }
            }
        }
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