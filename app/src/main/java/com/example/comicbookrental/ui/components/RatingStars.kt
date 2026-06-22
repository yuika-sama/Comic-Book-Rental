package com.example.comicbookrental.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import kotlin.math.floor
import kotlin.math.roundToInt


@Composable
fun RatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
    starSize: Dp = Dimens.Icon.Small,
    tint: Color = MaterialTheme.extendedColors.rating,
) {
    val rounded = (rating * 2).roundToInt() / 2f
    val full = floor(rounded).toInt()
    val hasHalf = (rounded - full) >= 0.5f

    Row(modifier = modifier) {
        repeat(5) { index ->
            val icon = when {
                index < full -> Icons.Filled.Star
                index == full && hasHalf -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Outlined.StarOutline
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(starSize),
            )
        }
    }
}
