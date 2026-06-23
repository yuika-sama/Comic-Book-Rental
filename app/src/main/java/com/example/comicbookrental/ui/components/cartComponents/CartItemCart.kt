package com.example.comicbookrental.ui.components.cartComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.models.CartItem
import com.example.comicbookrental.data.models.rentalDays
import com.example.comicbookrental.data.models.totalPrice
import com.example.comicbookrental.ui.components.ComicButton
import com.example.comicbookrental.ui.components.ComicButtonVariant
import com.example.comicbookrental.ui.components.CartComicCover
import com.example.comicbookrental.ui.components.comicHardShadow
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.utils.toDisplayDate
import com.example.comicbookrental.ui.utils.toVnd

@Composable
fun CartItemCard(
    item: CartItem,
    onEditDatesClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .comicHardShadow(
                shape = shape,
                offset = Dimens.Elevation.Resting,
                color = ink
            )
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = Dimens.Border.Standard,
                color = ink,
                shape = shape
            )
            .padding(Dimens.Spacing.ScreenPadding),
        horizontalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.StackMd
        )
    ) {
        CartComicCover(
            imageUrl = item.comicCoverUrl,
            title = item.comicTitle,
            modifier = Modifier
                .width(90.dp)
                .height(135.dp)
                .clip(RoundedCornerShape(Dimens.Radius.Button))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(Dimens.Radius.Button)
                )
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                Dimens.Spacing.ListItemSpacing
            )
        ) {
            Text(
                text = item.comicTitle.uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.comicAuthor,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "${item.rentalDays()} DAYS",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = Anton
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "${item.startDate.toDisplayDate()} → " +
                        item.endDate.toDisplayDate(),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = item.totalPrice().toVnd(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    Dimens.Spacing.ListItemSpacing
                )
            ) {
                ComicButton(
                    text = "Edit",
                    onClick = onEditDatesClick,
                    modifier = Modifier.weight(1f),
                    variant = ComicButtonVariant.Secondary
                )

                ComicButton(
                    text = "Remove",
                    onClick = onRemoveClick,
                    modifier = Modifier.weight(1f),
                    variant = ComicButtonVariant.Secondary
                )
            }
        }
    }
}