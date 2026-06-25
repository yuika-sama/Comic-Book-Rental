package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
    selected: Boolean = false,
    height: Dp = Dimens.Sizes.ButtonHeight,
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall.copy(fontFamily = Anton),
    horizontalPadding: Dp = Dimens.Spacing.SectionSpacing,
    onClick: () -> Unit = {},
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    val effectiveFilled = filled || selected
    val container = if (effectiveFilled) accent else MaterialTheme.colorScheme.surface
    val content = if (effectiveFilled) MaterialTheme.colorScheme.onPrimary else accent
    val frame = if (effectiveFilled) ink else accent

    val rest = Dimens.Elevation.Resting
    val press by animateDpAsState(targetValue = if (selected) rest else 0.dp, label = "genrePress")

    Box(
        modifier = modifier
            .height(height)
            .offset(x = press, y = press)
            .comicHardShadow(shape = shape, offset = rest - press, color = frame)
            .clip(shape)
            .background(container)
            .border(width = Dimens.Border.Standard, color = frame, shape = shape)
            .clickable(onClick = onClick)
            .padding(horizontal = horizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label.uppercase(),
            style = textStyle,
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
