package com.example.comicbookrental.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
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
