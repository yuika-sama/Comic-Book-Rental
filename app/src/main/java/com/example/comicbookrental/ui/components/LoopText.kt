package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.Anton

@Composable
fun LoopText(
    value: String
){
    Box(
        modifier =  Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 12.dp)
    ){
        Text(
            text = value,
            color = Color.White,
            fontFamily = Anton,
            fontSize = 18.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.basicMarquee(
                iterations = Int.MAX_VALUE,
                velocity = 30.dp
            )
        )
    }
}