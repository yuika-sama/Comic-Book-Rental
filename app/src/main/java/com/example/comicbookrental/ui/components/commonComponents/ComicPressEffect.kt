package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.theme.Dimens

@Immutable
data class ComicPressState(val shadowOffset: Dp, val translation: Dp)

@Composable
fun rememberComicPressState(
    interactionSource: MutableInteractionSource,
    restingOffset: Dp = Dimens.Elevation.Resting,
): ComicPressState {
    val pressed by interactionSource.collectIsPressedAsState()
    val shadowOffset by animateDpAsState(
        targetValue = if (pressed) 0.dp else restingOffset,
        label = "comicPressShadow",
    )
    val translation by animateDpAsState(
        targetValue = if (pressed) restingOffset else 0.dp,
        label = "comicPressTranslation",
    )
    return ComicPressState(shadowOffset = shadowOffset, translation = translation)
}
