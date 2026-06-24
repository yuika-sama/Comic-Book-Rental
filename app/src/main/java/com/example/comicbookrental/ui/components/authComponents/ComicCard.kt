package com.example.comicbookrental.ui.components.authComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(Dimens.Radius.Card),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.extendedColors.ink,
    borderWidth: Dp = Dimens.Border.Standard,
    shadowOffset: Dp = Dimens.Elevation.Resting,
    contentPadding: PaddingValues = PaddingValues(Dimens.Spacing.ContentSpacing),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .comicHardShadow(shape = shape, offset = shadowOffset, color = borderColor)
            .clip(shape)
            .background(containerColor)
            .border(width = borderWidth, color = borderColor, shape = shape)
            .padding(contentPadding),
        content = content,
    )
}
