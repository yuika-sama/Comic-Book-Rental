package com.example.comicbookrental.ui.components.checkoutComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.rentalDays
import com.example.comicbookrental.data.entities.totalPrice
import com.example.comicbookrental.ui.components.cartComponents.CartComicCover
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.ui.utils.toDisplayDate
import com.example.comicbookrental.ui.utils.toVnd

@Composable
 fun CheckoutItemSummary(
    item: CartItem
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = Dimens.Border.Standard,
                color = ink,
                shape = shape
            )
            .padding(Dimens.Spacing.ContentSpacing),
        horizontalArrangement = Arrangement.spacedBy(
            Dimens.Spacing.ContentSpacing
        )
    ) {
        CartComicCover(
            imageUrl = item.comicCoverUrl,
            title = item.comicTitle,
            modifier = Modifier
                .height(105.dp)
                .weight(0.28f)
        )

        Column(
            modifier = Modifier.weight(0.72f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.comicTitle.uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.comicAuthor,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "${item.startDate.toDisplayDate()} → ${item.endDate.toDisplayDate()}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "${item.rentalDays()} DAYS",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = Anton
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = item.totalPrice().toVnd(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Anton
                )
            )
        }
    }
}