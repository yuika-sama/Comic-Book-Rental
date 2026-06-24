package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun SynopsisCard(
    text: String,
    modifier: Modifier = Modifier,
    label: String = "SYNOPSIS",
) {
    val shape = RoundedCornerShape(Dimens.Radius.Default)
    val ink = MaterialTheme.extendedColors.ink

    Column(
        modifier = modifier
            .comicHardShadow(shape = shape, color = ink)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(Dimens.Spacing.StackMd),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = ink,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}