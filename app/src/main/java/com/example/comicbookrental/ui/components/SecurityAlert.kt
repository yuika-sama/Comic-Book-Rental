package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.Background
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Primary
import kotlinx.coroutines.sync.Mutex

@Composable
fun SecurityAlert(
    message: String
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, InkBlack)
            .background(Background)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .width(15.dp)
                .fillMaxHeight()
                .drawBehind{
                    val stripeWidth = 5.dp.toPx()
                    var x = 0f
                    while (x < size.width + size.height){
                        drawLine(
                            color = Primary,
                            start = Offset(x, 0f),
                            end = Offset(x - size.height, size.height),
                            strokeWidth = stripeWidth
                        )
                        x += stripeWidth * 2
                    }
                }
                .border(width = 2.dp, color = InkBlack)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                    append("SECURITY ALERT\n")
                }
                append(message)
            },
            fontSize = 13.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}