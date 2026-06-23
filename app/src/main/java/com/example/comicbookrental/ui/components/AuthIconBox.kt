package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun AuthIconBox(
    icon: ImageVector
){
    Box(
        modifier = Modifier
            .size(70.dp)
            .background(Color.Black)
    ){
        Box(
            modifier = Modifier
                .offset(x = (-4).dp, y = (-4).dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}