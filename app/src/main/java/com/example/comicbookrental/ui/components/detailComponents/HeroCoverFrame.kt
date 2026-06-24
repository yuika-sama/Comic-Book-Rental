package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun HeroCoverFrame(
    modifier: Modifier = Modifier,
    badgeText: String? = "HOT PICK",
    backPanelColor: Color = MaterialTheme.colorScheme.secondary,
    overhang: Dp = Dimens.Spacing.ContentSpacing,
    cover: @Composable BoxScope.() -> Unit = { CoverPlaceholder() },
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink
    Box(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .comicHardShadow(shape = shape, offset = -overhang, color = backPanelColor)
                .clip(shape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .border(Dimens.Border.Standard, ink, shape),
            content = cover,
        )


        if (badgeText != null) {
            HotPickBadge(
                text = badgeText,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = overhang, y = -overhang*2),
            )
        }
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

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360, heightDp = 400)
@Composable
private fun HeroCoverFramePreview() {
    ComicBookRentalTheme {
        HeroCoverFrame(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.Margin),
        )
    }
}
