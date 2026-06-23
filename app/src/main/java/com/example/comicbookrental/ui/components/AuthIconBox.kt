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
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun AuthIconBox(
    icon: ImageVector
){
    val shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink
    val shadowOffset = Dimens.Elevation.Resting

    Box(
        modifier = Modifier
            .size(Dimens.Sizes.AuthIconBoxSize)
            .background(ink, shape)
    ){
        Box(
            modifier = Modifier
                .offset(x = -shadowOffset, y = -shadowOffset)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary, shape)
                .border(Dimens.Border.Standard, ink, shape),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(Dimens.Icon.Large)
            )
        }
    }
}