package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun GenreCard(
    label: String,
    modifier: Modifier = Modifier,
    accent: Color = MaterialTheme.colorScheme.primary,
    filled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    val container = if (filled) accent else MaterialTheme.colorScheme.surface
    val content = if (filled) MaterialTheme.colorScheme.onPrimary else accent
    val frame = if (filled) ink else accent

    Box(
        modifier = modifier
            .height(Dimens.Sizes.ButtonHeight)
            .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = frame)
            .clip(shape)
            .background(container)
            .border(width = Dimens.Border.Standard, color = frame, shape = shape)
            .clickable(onClick = onClick)
            .padding(horizontal = Dimens.Spacing.SectionSpacing),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Anton),
            color = content,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun GenreCardPreview() {
    ComicBookRentalTheme {
        Row(
            modifier = Modifier.padding(Dimens.Spacing.ScreenPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GenreCard(label = "Action", accent = MaterialTheme.colorScheme.primary, filled = true)
            GenreCard(label = "Sci-Fi", accent = MaterialTheme.colorScheme.secondary, filled = false)
            GenreCard(label = "Horror", accent = MaterialTheme.colorScheme.secondary, filled = true)
        }
    }
}
