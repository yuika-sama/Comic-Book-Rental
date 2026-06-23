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
import androidx.compose.material.icons.outlined.Bolt
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
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun BrutalistButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val shape = RoundedCornerShape(Dimens.Radius.Button)
    val ink = MaterialTheme.extendedColors.ink
    val containerColor = MaterialTheme.colorScheme.primary
    val contentColor = MaterialTheme.colorScheme.onPrimary
    val restingOffset = Dimens.Elevation.Resting

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 0.dp else restingOffset,
        label = "brutalistButtonShadow",
    )
    val pressTranslation by animateDpAsState(
        targetValue = if (pressed) restingOffset else 0.dp,
        label = "brutalistButtonTranslation",
    )

    Box(
        modifier = modifier.padding(end = restingOffset, bottom = restingOffset),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Sizes.ButtonHeight)
                .offset(x = pressTranslation, y = pressTranslation)
                .comicHardShadow(shape = shape, offset = shadowOffset, color = ink)
                .clip(shape)
                .background(containerColor)
                .border(width = Dimens.Border.Standard, color = ink, shape = shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(horizontal = Dimens.Spacing.SectionSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = Anton),
                color = contentColor
            )

            Spacer(modifier = Modifier.width(Dimens.Spacing.StackSm))

            Icon(
                imageVector = Icons.Outlined.Bolt,
                contentDescription = null,
                tint = contentColor
            )
        }
    }
}