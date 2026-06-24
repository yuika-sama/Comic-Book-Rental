package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.models.Rental
import com.example.comicbookrental.data.models.RentalStatus
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.components.rentalComponents.RentalStatusChip
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import kotlin.math.ceil

private const val DAY_IN_MILLIS = 24 * 60 * 60 * 1000L

@Composable
fun RentalCard(
    rental: Rental,
    onReadClick: (Rental) -> Unit,
    onExtendClick: (Rental) -> Unit,
    modifier: Modifier = Modifier
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    val isExpired = rental.status == RentalStatus.EXPIRED ||
            rental.dueDate <= System.currentTimeMillis()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .comicHardShadow(
                shape = shape,
                offset = Dimens.Elevation.Resting,
                color = ink
            )
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = Dimens.Border.Standard,
                color = ink,
                shape = shape
            )
    ) {
        CartComicCover(
            imageUrl = rental.comicCoverUrl,
            title = rental.comicTitle,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(Dimens.Radius.Card))
                .border(
                    width = Dimens.Border.Focused,
                    color = ink,
                    shape = shape
                )
        )

        Column(
            modifier = Modifier.padding(Dimens.Spacing.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = rental.comicTitle.uppercase(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = Anton
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Rental #${rental.rentalId}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                RentalStatusChip(
                    status = if (isExpired) {
                        RentalStatus.EXPIRED
                    } else {
                        RentalStatus.ACTIVE
                    }
                )
            }

            Text(
                text = "Rented: ${rental.rentalDate.toFormattedDate()}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Due: ${rental.dueDate.toFormattedDate()}",
                style = MaterialTheme.typography.bodyMedium
            )

            RentalTimeRemaining(
                rental = rental,
                isExpired = isExpired
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    Dimens.Spacing.StackMd
                )
            ) {
                ComicButton(
                    text = "Read Now",
                    onClick = { onReadClick(rental) },
                    enabled = !isExpired,
                    modifier = Modifier.weight(1f),
                    variant = ComicButtonVariant.Primary
                )

                ComicButton(
                    text = "Extend",
                    onClick = { onExtendClick(rental) },
                    modifier = Modifier.weight(1f),
                    variant = ComicButtonVariant.Secondary
                )
            }
        }
    }
}


@Composable
private fun RentalTimeRemaining(
    rental: Rental,
    isExpired: Boolean
) {
    val now = System.currentTimeMillis()

    val totalDuration = (rental.dueDate - rental.rentalDate)
        .coerceAtLeast(1L)

    val remainingDuration = (rental.dueDate - now)
        .coerceIn(0L, totalDuration)

    val progress = remainingDuration.toFloat() / totalDuration.toFloat()

    val daysLeft = ceil(
        remainingDuration.toDouble() / DAY_IN_MILLIS
    ).toInt()

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "TIME REMAINING",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = Anton
                )
            )

            Text(
                text = if (isExpired) "EXPIRED" else "$daysLeft DAYS LEFT",
                style = MaterialTheme.typography.labelSmall,
                color = if (isExpired) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .border(
                    width = Dimens.Border.Standard,
                    color = MaterialTheme.extendedColors.ink
                ),
            color = if (isExpired) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            },
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

private fun Long.toFormattedDate(): String {
    return java.text.SimpleDateFormat(
        "dd/MM/yyyy",
        java.util.Locale.getDefault()
    ).format(java.util.Date(this))
}