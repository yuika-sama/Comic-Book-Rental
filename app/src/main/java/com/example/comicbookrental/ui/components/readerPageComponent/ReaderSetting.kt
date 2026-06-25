package com.example.comicbookrental.ui.components.readerPageComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.comicbookrental.ui.screens.reader_screen.ReadingMode
import com.example.comicbookrental.ui.theme.Dimens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Primary
import kotlin.math.roundToInt

@Composable
 fun ReaderSettingsSheet(
    readingMode: ReadingMode,
    zoom: Float,
    brightness: Float,
    onReadingModeChange: (ReadingMode) -> Unit,
    onZoomOutClick: () -> Unit,
    onZoomInClick: () -> Unit,
    onResetZoomClick: () -> Unit,
    onBrightnessChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.Spacing.ScreenPadding,
                vertical = Dimens.Spacing.SectionSpacing
            ),
        verticalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ContentSpacing
        )
    ) {
        Text(
            text = "READING SETTINGS",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = Anton
            ),
            color = Primary
        )

        Text(
            text = "READING MODE",
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = Anton
            )
        )

        ReaderModeSelector(
            selectedMode = readingMode,
            onModeChange = onReadingModeChange
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ZOOM: ${(zoom * 100).roundToInt()}%",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = Anton
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onZoomOutClick,
                    enabled = zoom > 1f
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Zoom out"
                    )
                }

                IconButton(
                    onClick = onZoomInClick,
                    enabled = zoom < 3f
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Zoom in"
                    )
                }

                TextButton(
                    onClick = onResetZoomClick
                ) {
                    Text("RESET")
                }
            }
        }

        Text(
            text = "PINCH WITH TWO FINGERS ON THE PAGE",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "BRIGHTNESS: ${(brightness * 100).roundToInt()}%",
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = Anton
            )
        )

        Slider(
            value = brightness,
            onValueChange = onBrightnessChange,
            valueRange = 0.25f..1f
        )
    }

}