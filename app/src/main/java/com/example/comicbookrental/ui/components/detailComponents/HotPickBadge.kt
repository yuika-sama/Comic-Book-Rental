package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
fun HotPickBadge(
    text: String = "HOT PICK",
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink
    Box(
        modifier = modifier
            .comicHardShadow(shape = shape, color = ink)
            .clip(shape)
            .background(containerColor)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(horizontal = Dimens.Spacing.ContentSpacing, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(fontFamily = Anton),
            color = contentColor,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun HotPickBadgePreview() {
    ComicBookRentalTheme {
        HotPickBadge(modifier = Modifier.padding(Dimens.Spacing.StackLg))
    }
}
