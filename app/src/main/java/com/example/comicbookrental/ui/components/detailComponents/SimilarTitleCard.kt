package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun SimilarTitleCard(
    title: String,
    price: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    coverHeight: Dp = 200.dp,
    cover: @Composable BoxScope.() -> Unit = { CoverPlaceholder() },
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = modifier.clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight)
                .comicHardShadow(shape = shape, offset = Dimens.Elevation.Resting, color = ink)
                .clip(shape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(width = Dimens.Border.Standard, color = ink, shape = shape),
            content = cover,
        )

        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = price,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun BoxScope.CoverPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun SimilarTitleCardPreview() {
    ComicBookRentalTheme {
        SimilarTitleCard(
            title = "Detective X: Ghost Protocol",
            price = "$1.50",
            modifier = Modifier
                .padding(Dimens.Spacing.Margin)
                .width(Dimens.Sizes.SimilarCardWidth),
        )
    }
}
