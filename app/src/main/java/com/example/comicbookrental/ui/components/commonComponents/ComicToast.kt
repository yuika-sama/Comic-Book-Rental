package com.example.comicbookrental.ui.components.commonComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import kotlinx.coroutines.delay


enum class ComicToastType { SUCCESS, ERROR, WARNING, INFO }


@Composable
fun ComicToast(
    message: String,
    modifier: Modifier = Modifier,
    type: ComicToastType = ComicToastType.SUCCESS,
    title: String? = null,
    onDismiss: (() -> Unit)? = null,
) {
    val ink = MaterialTheme.extendedColors.ink
    val accent = type.accentColor()
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Row(
        modifier = modifier
            .comicHardShadow(shape = shape, color = ink)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(Dimens.Border.Standard, ink, shape)
            .padding(Dimens.Spacing.StackSm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        // Color-coded icon badge — the "popped" status indicator.
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(Dimens.Radius.Inner))
                .background(accent)
                .border(Dimens.Border.Standard, ink, RoundedCornerShape(Dimens.Radius.Inner)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = type.icon(),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(Dimens.Icon.Medium),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = (title ?: type.defaultTitle()).uppercase(),
                fontFamily = Anton,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp,
                color = accent,
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = ink,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (onDismiss != null) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Dismiss",
                tint = ink,
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.Radius.Sm))
                    .clickable(onClick = onDismiss)
                    .padding(Dimens.Spacing.StackSm / 2)
                    .size(Dimens.Icon.Medium),
            )
        }
    }
}

@Composable
private fun ComicToastType.accentColor(): Color = when (this) {
    ComicToastType.SUCCESS -> MaterialTheme.extendedColors.success
    ComicToastType.ERROR -> MaterialTheme.colorScheme.error
    ComicToastType.WARNING -> MaterialTheme.extendedColors.warning
    ComicToastType.INFO -> MaterialTheme.extendedColors.info
}

private fun ComicToastType.icon(): ImageVector = when (this) {
    ComicToastType.SUCCESS -> Icons.Filled.Check
    ComicToastType.ERROR -> Icons.Filled.PriorityHigh
    ComicToastType.WARNING -> Icons.Filled.Warning
    ComicToastType.INFO -> Icons.Filled.Info
}

private fun ComicToastType.defaultTitle(): String = when (this) {
    ComicToastType.SUCCESS -> "Success"
    ComicToastType.ERROR -> "Oops"
    ComicToastType.WARNING -> "Heads up"
    ComicToastType.INFO -> "FYI"
}


data class ComicToastData(
    val message: String,
    val type: ComicToastType = ComicToastType.SUCCESS,
    val title: String? = null,
)

class ComicToastState {
    var current by mutableStateOf<ComicToastData?>(null)
        private set

    fun show(message: String, type: ComicToastType = ComicToastType.SUCCESS, title: String? = null) {
        current = ComicToastData(message, type, title)
    }

    fun dismiss() {
        current = null
    }
}

@Composable
fun rememberComicToastState(): ComicToastState = remember { ComicToastState() }


@Composable
fun ComicToastHost(
    state: ComicToastState,
    modifier: Modifier = Modifier,
    durationMillis: Long = 2_500L,
) {
    val data = state.current

    LaunchedEffectDismiss(data, durationMillis) { state.dismiss() }

    var shown by remember { mutableStateOf(data) }
    if (data != null) shown = data

    AnimatedVisibility(
        visible = data != null,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut(),
        modifier = modifier,
    ) {
        shown?.let {
            ComicToast(
                message = it.message,
                type = it.type,
                title = it.title,
                onDismiss = { state.dismiss() },
            )
        }
    }
}

@Composable
private fun LaunchedEffectDismiss(
    data: ComicToastData?,
    durationMillis: Long,
    onTimeout: () -> Unit,
) {
    androidx.compose.runtime.LaunchedEffect(data) {
        if (data != null) {
            delay(durationMillis)
            onTimeout()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun ComicToastPreview() {
    ComicBookRentalTheme {
        Column(
            modifier = Modifier.padding(Dimens.Spacing.Margin),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            ComicToast(
                message = "Added \"Solo Leveling\" to your cart.",
                type = ComicToastType.SUCCESS,
                onDismiss = {},
            )
            ComicToast(message = "Rental confirmed — enjoy the read!", type = ComicToastType.SUCCESS)
            ComicToast(message = "Something went wrong. Try again.", type = ComicToastType.ERROR)
            ComicToast(message = "This issue expires in 2 days.", type = ComicToastType.WARNING)
            ComicToast(message = "New chapters drop every Friday.", type = ComicToastType.INFO)
        }
    }
}
