package com.example.comicbookrental.ui.components.detailComponents
import com.example.comicbookrental.ui.components.commonComponents.comicHardShadow
import com.example.comicbookrental.ui.components.commonComponents.ComicButton

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun RentOptionCard(
    title: String,
    price: String,
    subtitle: String,
    ctaText: String,
    onRent: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
    highlighted: Boolean = false,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Md)
    val ink = MaterialTheme.extendedColors.ink

    val container = if (highlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val onContainer = if (highlighted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val priceColor = if (highlighted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    val subtitleColor = if (highlighted) {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val actionContainer = MaterialTheme.colorScheme.surface

    Column(
        modifier = modifier
            .comicHardShadow(shape = shape, color = ink)
            .clip(shape)
            .background(container)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(Dimens.Spacing.StackMd),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.GridUnit)) {
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = onContainer,
                )
                Text(
                    text = subtitle.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = subtitleColor,
                )
            }
            Text(
                text = price,
                style = MaterialTheme.typography.headlineSmall,
                color = priceColor,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ComicButton(
                text = ctaText,
                onClick = onRent,
                modifier = Modifier.weight(1f),
                containerColor = actionContainer,
                contentColor = ink,
            )
            CartIconButton(
                onClick = onAddToCart,
                containerColor = actionContainer,
                contentColor = ink,
                ink = ink,
            )
        }
    }
}

/** Square, brutalist "add to cart" button that mirrors [ComicButton]'s press feel. */
@Composable
private fun CartIconButton(
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    ink: Color,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Button)

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 0.dp else Dimens.Elevation.Resting,
        label = "cartButtonShadow",
    )
    val pressTranslation by animateDpAsState(
        targetValue = if (pressed) Dimens.Elevation.Resting else 0.dp,
        label = "cartButtonTranslation",
    )

    Box(
        modifier = modifier
            .size(Dimens.Sizes.ButtonHeight)
            .offset(x = pressTranslation, y = pressTranslation)
            .comicHardShadow(shape = shape, offset = shadowOffset, color = ink)
            .clip(shape)
            .background(containerColor)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.AddShoppingCart,
            contentDescription = "Add to cart",
            tint = contentColor,
            modifier = Modifier.size(Dimens.Icon.Medium),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun RentOptionCardPreview() {
    ComicBookRentalTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.Spacing.Margin),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            RentOptionCard(
                title = "Single Issue",
                price = "$1.99",
                subtitle = "7 Days Access",
                ctaText = "Rent Now",
                onRent = {},
                onAddToCart = {},
            )
            RentOptionCard(
                title = "Full Arc (S1)",
                price = "$14.99",
                subtitle = "Permanent Library",
                ctaText = "Rent Season",
                onRent = {},
                onAddToCart = {},
                highlighted = true,
            )
        }
    }
}
