package com.example.comicbookrental.ui.components.searchComponents
import com.example.comicbookrental.ui.components.comicHardShadow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun ComicSearchField(
    shadowOffset: Dp = Dimens.Elevation.Resting,
    borderColor: Color = MaterialTheme.extendedColors.ink,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search for heroes, villains, or epics",
) {
    val shape = RoundedCornerShape(Dimens.Radius.Sm)
    val ink = MaterialTheme.extendedColors.ink

    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val borderWidth = if (focused) Dimens.Border.Focused else Dimens.Border.Standard

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.Sizes.LargeButtonHeight)
            .comicHardShadow(shape = shape, offset = shadowOffset, color = borderColor)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .border(width = borderWidth, color = ink, shape = shape),
        singleLine = true,
        interactionSource = interactionSource,
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = ImeAction.Search),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = Dimens.Spacing.ContentSpacing),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimens.Icon.Medium),
                )
                Box(
                    modifier = Modifier
                        .padding(start = Dimens.Spacing.ListItemSpacing)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder.uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    innerTextField()
                }
            }
        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8)
@Composable
private fun ComicSearchFieldPreview() {
    ComicBookRentalTheme {
        ComicSearchField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(Dimens.Spacing.ScreenPadding),
        )
    }
}
