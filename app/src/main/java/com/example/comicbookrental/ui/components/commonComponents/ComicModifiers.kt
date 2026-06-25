package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.InkBlack

fun Modifier.comicHardShadow(
    shape: Shape = RoundedCornerShape(Dimens.Radius.Card),
    offset: DpOffset = Dimens.Elevation.Resting,
    color: Color = InkBlack,
): Modifier = this.drawBehind {
    val outline = shape.createOutline(size, layoutDirection, this)
    translate(left = offset.toPx(), top = offset.toPx()) {
        drawOutline(outline = outline, color = color)
    }
}

/**
 * A subtle halftone (dot) texture drawn behind the content — the "nod to the printing process"
 * from DESIGN.md, used as the background of secondary surfaces / section gutters.
 *
 * Draws a regular grid of [dotColor] dots at low [alpha]. Layer it AFTER a solid
 * `.background(...)` so the dots sit on top of the paper fill.
 */
fun Modifier.halftoneBackground(
    dotColor: Color = InkBlack,
    dotRadius: Dp = 1.dp,
    spacing: Dp = 8.dp,
    alpha: Float = 0.08f,
): Modifier = this.drawBehind {
    val radius = dotRadius.toPx()
    val step = spacing.toPx()
    val tinted = dotColor.copy(alpha = alpha)
    var y = 0f
    while (y <= size.height)
    {
        var x = 0f
        while (x <= size.width)
        {
            drawCircle(color = tinted, radius = radius, center = Offset(x, y))
            x += step
        }
        y += step
    }
}

/**
 * Diagonal "caution-tape" stripes drawn behind the content — used for warning / info banners.
 * Layer it AFTER a `.clip(shape)` (so the stripes respect rounded corners) and a solid
 * `.background(...)`, with the `.border(...)` after it.
 */
fun Modifier.hazardStripes(
    stripeColor: Color = InkBlack,
    stripeWidth: Dp = 10.dp,
    gap: Dp = 10.dp,
    alpha: Float = 0.18f,
): Modifier = this.drawBehind {
    val tinted = stripeColor.copy(alpha = alpha)
    val thickness = stripeWidth.toPx()
    val period = thickness + gap.toPx()
    // 45° lines sweeping across; offset the start by height so the whole face is covered.
    var x = 0f
    while (x <= size.width + size.height)
    {
        drawLine(
            color = tinted,
            start = Offset(x, 0f),
            end = Offset(x - size.height, size.height),
            strokeWidth = thickness,
        )
        x += period
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

    for (i in 0..cols)
    {
        for (j in 0..rows)
        {
            drawCircle(
                color = dotColor,
                radius = radiusPx,
                center = Offset(startX + i * spacingPx, startY + j * spacingPx)
            )
        }
    }
}
