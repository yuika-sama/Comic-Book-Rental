package com.example.comicbookrental.ui.screens.cart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.comicbookrental.data.models.MILLIS_PER_DAY
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.utils.toDisplayDate

private const val MAX_RENTAL_DAYS = 30L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDateRangePickerDialog(
    comicTitle: String,
    initialStartDate: Long,
    initialEndDate: Long,
    onDismiss: () -> Unit,
    onConfirm: (Long, Long) -> Unit
) {
    val pickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = initialStartDate,
        initialSelectedEndDateMillis = initialEndDate
    )

    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    val startDate = pickerState.selectedStartDateMillis
    val endDate = pickerState.selectedEndDateMillis

    var errorText by remember {
        mutableStateOf<String?>(null)
    }

    val selectedDays = if (
        startDate != null &&
        endDate != null &&
        endDate > startDate
    ) {
        ((endDate - startDate) / MILLIS_PER_DAY)
            .coerceAtLeast(1L)
    } else {
        null
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.ScreenPadding)
                .comicHardShadow(
                    shape = shape,
                    offset = Dimens.Elevation.Resting,
                    color = ink
                )
                .border(
                    width = Dimens.Border.Standard,
                    color = ink,
                    shape = shape
                ),
            shape = shape,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column {
                Column(
                    modifier = Modifier.padding(
                        horizontal = Dimens.Spacing.ScreenPadding,
                        vertical = Dimens.Spacing.ContentSpacing
                    ),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "EDIT RENTAL DATES",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = Anton
                        )
                    )

                    Text(
                        text = comicTitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.padding(top = 2.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            Dimens.Spacing.ListItemSpacing
                        )
                    ) {
                        RentalDateInfo(
                            label = "START",
                            value = startDate?.toDisplayDate() ?: "Choose date",
                            modifier = Modifier.weight(1f)
                        )

                        RentalDateInfo(
                            label = "END",
                            value = endDate?.toDisplayDate() ?: "Choose date",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    selectedDays?.let { days ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(Dimens.Radius.Button)
                        ) {
                            Text(
                                text = "$days DAYS RENTAL",
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 6.dp
                                ),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontFamily = Anton
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }

                HorizontalDivider(
                    color = ink.copy(alpha = 0.2f)
                )

                DateRangePicker(
                    state = pickerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    title = null,
                    headline = null,
                    showModeToggle = false
                )

                errorText?.let { error ->
                    Text(
                        text = error,
                        modifier = Modifier.padding(
                            horizontal = Dimens.Spacing.ScreenPadding
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.Spacing.ScreenPadding),
                    horizontalArrangement = Arrangement.spacedBy(
                        Dimens.Spacing.ContentSpacing
                    )
                ) {


                    ComicButton(
                        text = "Save Dates",
                        enabled = startDate != null && endDate != null,
                        modifier = Modifier.weight(1f),
                        variant = ComicButtonVariant.Primary,
                        onClick = {
                            when {
                                startDate == null || endDate == null -> {
                                    errorText = "Please select both dates."
                                }

                                endDate <= startDate -> {
                                    errorText = "End date must be after start date."
                                }

                                endDate - startDate >
                                        MAX_RENTAL_DAYS * MILLIS_PER_DAY -> {
                                    errorText = "Maximum rental duration is 30 days."
                                }

                                else -> {
                                    onConfirm(startDate, endDate)
                                }
                            }
                        }
                    )
                    ComicButton(
                        text = "Cancel",
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        variant = ComicButtonVariant.Secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun RentalDateInfo(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = Anton
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}