package com.example.comicbookrental.ui.components.rentalComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun ExtendRentalDialog(
    rentalTitle: String,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedDays by remember {
        mutableIntStateOf(7)
    }


    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .comicHardShadow(
                    shape = shape,
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.Spacing.ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(
                    Dimens.Spacing.ContentSpacing
                )
            ) {
                Text(
                    text = "EXTEND RENTAL",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = Anton
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.28f)
                        .height(4.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = rentalTitle.uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = Anton
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Choose how many extra days you want to rent this comic.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        Dimens.Spacing.ContentSpacing
                    )
                ) {
                    listOf(3, 7, 14).forEach { days ->
                        ExtendDaysOption(
                            days = days,
                            selected = selectedDays == days,
                            onClick = {
                                selectedDays = days
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Text(
                    text = "YOU SELECTED: $selectedDays DAYS",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = Anton
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                ComicButton(
                    text = "Continue To Checkout",
                    onClick = {
                        onConfirm(selectedDays)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    variant = ComicButtonVariant.Primary
                )

                ComicButton(
                    text = "Cancel",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ComicButtonVariant.Secondary
                )
            }
        }
    }


}

@Composable
private fun ExtendDaysOption(
    days: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Button)


    Column(
        modifier = modifier
            .clip(shape)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            )
            .border(
                width = if (selected) {
                    Dimens.Border.Focused
                } else {
                    Dimens.Border.Standard
                },
                color = ink,
                shape = shape
            )
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "$days",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = Anton
            ),
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )

        Text(
            text = "DAYS",
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = Anton
            )
        )
    }
}
