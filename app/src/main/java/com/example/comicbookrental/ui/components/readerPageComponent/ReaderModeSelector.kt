package com.example.comicbookrental.ui.components.readerPageComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.screens.reader_screen.ReadingMode
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun ReaderModeSelector(
    selectedMode: ReadingMode,
    onModeChange: (ReadingMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.Spacing.ScreenPadding),
        horizontalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ListItemSpacing
        )
    ) {
        ReaderModeButton(
            text = "↔ HORIZONTAL",
            selected = selectedMode == ReadingMode.HORIZONTAL,
            onClick = {
                onModeChange(ReadingMode.HORIZONTAL)
            },
            modifier = Modifier.weight(1f)
        )

        ReaderModeButton(
            text = "↕ VERTICAL",
            selected = selectedMode == ReadingMode.VERTICAL,
            onClick = {
                onModeChange(ReadingMode.VERTICAL)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ReaderModeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Button)

    Box(
        modifier = modifier
            .height(42.dp)
            .clip(shape)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = Dimens.Border.Standard,
                color = ink,
                shape = shape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = Anton
            ),
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}