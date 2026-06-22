package com.example.comicbookrental.ui.components

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.splitToIntList
import com.example.comicbookrental.ui.theme.HankenGrotesk

@Composable
fun BrutalistTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    trailingContent: (@Composable () -> Unit)? = null
){
    Column(
        modifier =  Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                text = label,
                fontFamily = HankenGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            trailingContent?.invoke()
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontFamily = HankenGrotesk,
                fontSize = 16.sp,
                color = Color.Black
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 2.dp, color = Color.Black)
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.Gray,
                                fontFamily = HankenGrotesk,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}