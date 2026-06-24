package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.InkBlack

@Composable
fun SolidShadowBox(
    modifier: Modifier = Modifier,
    shadowColor: Color = InkBlack,
    offsetX: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = offsetX, y = offsetY)
                .background(shadowColor)
                .border(1.dp, InkBlack)
        )
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .border(1.dp, InkBlack)
        ) {
            content()
        }
    }
}