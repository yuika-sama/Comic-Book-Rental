package com.example.comicbookrental.ui.components.homeComponents

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
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun RankBadge(
    rank: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    Box(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .border(Dimens.Border.Standard, MaterialTheme.extendedColors.ink, shape)
            .padding(horizontal = Dimens.Spacing.ListItemSpacing, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "#$rank",
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = Anton),
            color = contentColor,
        )
    }
}
