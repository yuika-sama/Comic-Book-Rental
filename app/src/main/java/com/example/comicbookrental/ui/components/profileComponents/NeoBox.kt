package com.example.comicbookrental.ui.components.profileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.InkBlack

@Composable
fun NeoBox(
    modifier: Modifier = Modifier,
    offset: Dp = 6.dp,
    backgroundColor: Color = Color.White,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = offset, y = offset)
                .background(InkBlack)
        )
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .border(2.dp, InkBlack)
        ) {
            content()
        }
    }
}