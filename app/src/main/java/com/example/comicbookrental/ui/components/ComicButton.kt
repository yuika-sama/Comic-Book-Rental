package com.example.comicbookrental.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

/** The two button styles defined in DESIGN.md → Components → Buttons. */
enum class ComicButtonVariant {
    /** Action Orange fill, white uppercase Anton, hard ink shadow. */
    Primary,

    /** Paper White fill, ink border, NO shadow. */
    Secondary,
}


@Composable
fun ComicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ComicButtonVariant = ComicButtonVariant.Primary,
    enabled: Boolean = true,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Button)
    val ink = MaterialTheme.extendedColors.ink

    val containerColor = when (variant) {
        ComicButtonVariant.Primary -> MaterialTheme.colorScheme.primary
        ComicButtonVariant.Secondary -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when (variant) {
        ComicButtonVariant.Primary -> MaterialTheme.colorScheme.onPrimary
        ComicButtonVariant.Secondary -> MaterialTheme.colorScheme.onSurface
    }
    // Secondary has no shadow per the spec.
    val restingOffset = if (variant == ComicButtonVariant.Primary) Dimens.Elevation.Resting else 0.dp

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    // When pressed: shadow collapses to 0 and the button moves into where the shadow was.
    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 0.dp else restingOffset,
        label = "comicButtonShadow",
    )
    val pressTranslation by animateDpAsState(
        targetValue = if (pressed) restingOffset else 0.dp,
        label = "comicButtonTranslation",
    )

    Row(
        modifier = modifier
            .height(Dimens.Sizes.ButtonHeight)
            .alpha(if (enabled) 1f else 0.4f)
            .offset(x = pressTranslation, y = pressTranslation)
            .comicHardShadow(shape = shape, offset = shadowOffset, color = ink)
            .clip(shape)
            .background(containerColor)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            )
            .padding(horizontal = Dimens.Spacing.SectionSpacing),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (variant == ComicButtonVariant.Primary) text.uppercase() else text,
            color = contentColor,
            textAlign = TextAlign.Center,
            style = if (variant == ComicButtonVariant.Primary) {
                // Uppercase Anton for the "Action" look.
                MaterialTheme.typography.titleMedium.copy(fontFamily = Anton)
            } else {
                MaterialTheme.typography.labelLarge
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun ComicButtonPreview() {
    ComicBookRentalTheme {
        Row(
            modifier = Modifier.padding(Dimens.Spacing.ScreenPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            ComicButton(text = "Rent now", onClick = {}, variant = ComicButtonVariant.Primary)
            ComicButton(text = "Details", onClick = {}, variant = ComicButtonVariant.Secondary)
        }
    }
}
