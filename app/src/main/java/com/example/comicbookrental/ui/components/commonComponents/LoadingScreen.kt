package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.InkBlack

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .halftoneBackground()
            .zIndex(100f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .comicHardShadow(
                    shape = RoundedCornerShape(0.dp),
                    offset = 6.dp,
                    color = InkBlack
                )
                .background(MaterialTheme.colorScheme.primary)
                .border(4.dp, InkBlack)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    color = InkBlack,
                    strokeWidth = 6.dp,
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "LOADING...",
                    fontFamily = Anton,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = InkBlack,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}
