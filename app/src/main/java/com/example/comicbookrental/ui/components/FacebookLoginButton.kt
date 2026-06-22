package com.example.comicbookrental.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Facebook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.AntonFont
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.Dimens.ShadowOffset.shadowOffset
import com.example.comicbookrental.ui.theme.FacebookColorPrimary

@Composable
fun FacebookLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .padding(end = shadowOffset, bottom = shadowOffset)
    ) {
        Row(
            modifier = Modifier
                .background(FacebookColorPrimary)
                .border(width = 3.dp, color = Color.Black)
                .clickable { onClick() }
                .padding(Dimens.Spacing.ContentSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.Facebook,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "FACEBOOK",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}