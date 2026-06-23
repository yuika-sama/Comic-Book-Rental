package com.example.comicbookrental.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Facebook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.FacebookColorPrimary
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun FacebookLoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val shape = RoundedCornerShape(Dimens.Radius.Button)
    val ink = MaterialTheme.extendedColors.ink
    val restingOffset = Dimens.Elevation.Resting

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 0.dp else restingOffset,
        label = "facebookButtonShadow",
    )
    val pressTranslation by animateDpAsState(
        targetValue = if (pressed) restingOffset else 0.dp,
        label = "facebookButtonTranslation",
    )

    Box(
        modifier = modifier
            .padding(end = restingOffset, bottom = restingOffset)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Sizes.ButtonHeight)
                .offset(x = pressTranslation, y = pressTranslation)
                .comicHardShadow(shape = shape, offset = shadowOffset, color = ink)
                .clip(shape)
                .background(FacebookColorPrimary)
                .border(width = Dimens.Border.Standard, color = ink, shape = shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(Dimens.Spacing.ContentSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.Facebook,
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = "FACEBOOK",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}