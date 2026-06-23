package com.example.comicbookrental.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.InkBlack

fun Modifier.comicHardShadow(
    shape: Shape = RoundedCornerShape(Dimens.Radius.Card),
    offset: Dp = Dimens.Elevation.Resting,
    color: Color = InkBlack,
): Modifier = this.drawBehind {
    val outline = shape.createOutline(size, layoutDirection, this)
    translate(left = offset.toPx(), top = offset.toPx()) {
        drawOutline(outline = outline, color = color)
    }
}

fun Modifier.comicHalftoneBackground(
    dotColor: Color,
    dotRadius: Dp = 2.dp,
    spacing: Dp = 12.dp
): Modifier = this.drawBehind {
    val radiusPx = dotRadius.toPx()
    val spacingPx = spacing.toPx()
    val cols = (size.width / spacingPx).toInt()
    val rows = (size.height / spacingPx).toInt()

    // Align pattern by centering the grid
    val startX = (size.width - cols * spacingPx) / 2
    val startY = (size.height - rows * spacingPx) / 2

    for (i in 0..cols) {
        for (j in 0..rows) {
            drawCircle(
                color = dotColor,
                radius = radiusPx,
                center = Offset(startX + i * spacingPx, startY + j * spacingPx)
            )
        }
    }
}
