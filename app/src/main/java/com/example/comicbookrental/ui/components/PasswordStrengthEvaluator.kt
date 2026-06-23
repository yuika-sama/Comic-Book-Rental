package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.Error
import com.example.comicbookrental.ui.theme.InkBlack
import com.example.comicbookrental.ui.theme.Success
import com.example.comicbookrental.ui.theme.TextSecondary
import com.example.comicbookrental.ui.theme.Warning

@Composable
fun PasswordStrengthEvaluator(
    strength: Int,
    label: String
)
{
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(4) { index ->
                val color = when
                {
                    index < strength && strength <= 1 -> Error
                    index < strength && strength <= 2 -> Warning
                    index < strength && strength <= 4 -> Success
                    else -> TextSecondary
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .border(1.dp, InkBlack)
                        .background(color)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = TextStyle(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = InkBlack
            )
        )
    }
}