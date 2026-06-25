package com.example.comicbookrental.ui.components.adminComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.ui.components.commonComponents.CartComicCover
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.Error
import com.example.comicbookrental.ui.theme.extendedColors
import com.example.comicbookrental.utils.toPriceLabel

@Composable
fun AdminComicCard(
    comic: Comic,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .border(width = Dimens.Border.Standard, color = ink, shape = shape)
            .padding(Dimens.Spacing.ContentSpacing),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(width = 64.dp, height = 88.dp)
                    .clip(RoundedCornerShape(Dimens.Radius.Inner))
                    .border(Dimens.Border.Hairline, ink, RoundedCornerShape(Dimens.Radius.Inner))
            ) {
                CartComicCover(url = comic.coverImageUrl, contentDescription = comic.title)
            }

            Spacer(modifier = Modifier.width(Dimens.Spacing.ContentSpacing))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = comic.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = Anton),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = comic.author.ifBlank { "—" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Dimens.Spacing.StackSm))
                Text(
                    text = comic.genreLabel.ifBlank { "Uncategorized" }.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(Dimens.Spacing.StackSm))
                Text(
                    text = "CH. ${comic.latestChapter}  •  ${comic.rentalPrice.toPriceLabel()}/day",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm)) {
            ComicButton(
                text = "Edit",
                onClick = onEdit,
                variant = ComicButtonVariant.Secondary,
                modifier = Modifier.weight(1f)
            )
            ComicButton(
                text = "Delete",
                onClick = onDelete,
                variant = ComicButtonVariant.Secondary,
                contentColor = Error,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
