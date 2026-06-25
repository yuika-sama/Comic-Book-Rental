package com.example.comicbookrental.ui.screens.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.ui.components.commonComponents.ComicButton
import com.example.comicbookrental.ui.components.commonComponents.ComicButtonVariant
import com.example.comicbookrental.ui.components.commonComponents.ComicToastHost
import com.example.comicbookrental.ui.components.commonComponents.ComicToastType
import com.example.comicbookrental.ui.components.commonComponents.ComicTopBar
import com.example.comicbookrental.ui.components.commonComponents.halftoneBackground
import com.example.comicbookrental.ui.components.commonComponents.rememberComicToastState
import com.example.comicbookrental.ui.components.detailComponents.HotPickBadge
import com.example.comicbookrental.ui.components.wishlistComponents.WishlistCard
import com.example.comicbookrental.ui.theme.Anton
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun WishlistRoute(
    onBack: () -> Unit,
    onComicClick: (String) -> Unit,
    onExplore: () -> Unit,
    onCartClick: () -> Unit,
    wishlistViewModel: WishlistViewModel = viewModel(),
) {
    val comics by wishlistViewModel.items.collectAsStateWithLifecycle()
    val toastState = rememberComicToastState()

    LaunchedEffect(Unit) { wishlistViewModel.refresh() }

    Box(modifier = Modifier.fillMaxSize()) {
        WishlistScreen(
            comics = comics,
            onBack = onBack,
            onComicClick = { onComicClick(it.id.toString()) },
            onRent = { onComicClick(it.id.toString()) },
            onRemove = { comic ->
                wishlistViewModel.toggle(comic)
                toastState.show(
                    message = "Removed \"${comic.title}\" from your wishlist.",
                    type = ComicToastType.INFO,
                )
            },
            onExplore = onExplore,
            onCartClick = onCartClick,
        )

        ComicToastHost(
            state = toastState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Dimens.Spacing.Margin),
        )
    }
}

@Composable
fun WishlistScreen(
    comics: List<Comic>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onComicClick: (Comic) -> Unit = {},
    onRent: (Comic) -> Unit = {},
    onRemove: (Comic) -> Unit = {},
    onExplore: () -> Unit = {},
    onCartClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .halftoneBackground(),
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(
                start = Dimens.Spacing.Margin,
                end = Dimens.Spacing.Margin,
                top = Dimens.Spacing.ContentSpacing,
                bottom = Dimens.Spacing.SectionSpacing,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            item {
                WishlistHeader()
            }

            items(items = comics, key = { it.id }) { comic ->
                WishlistCard(
                    title = comic.title,
                    author = comic.author,
                    rating = comic.avgRating,
                    coverUrl = comic.coverImageUrl,
                    onClick = { onComicClick(comic) },
                    onRent = { onRent(comic) },
                    onRemove = { onRemove(comic) },
                )
            }

            if (comics.isEmpty()) {
                item {
                    Text(
                        text = "No favorites yet — start your stash below.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = Dimens.Spacing.StackMd),
                    )
                }
            }

            item {
                WantMoreCard(onExplore = onExplore)
            }
        }
    }
}

@Composable
private fun WishlistHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm)) {
        HotPickBadge(text = "Interested")
        Text(
            text = "Your curated stash of high-octane adventures waiting to be devoured. " +
                "Grab them before they're gone!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** Dashed "stub" CTA at the foot of the list, nudging the user back into the catalog. */
@Composable
private fun WantMoreCard(onExplore: () -> Unit) {
    val ink = MaterialTheme.extendedColors.ink
    val shape = RoundedCornerShape(Dimens.Radius.Card)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .dashedBorder(color = ink, cornerRadius = Dimens.Radius.Card)
            .padding(Dimens.Spacing.StackLg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
    ) {
        Text(
            text = "WANT MORE?",
            style = MaterialTheme.typography.titleMedium.copy(fontFamily = Anton),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = "Browse the archives to find your next obsession.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        ComicButton(
            text = "Explore Now",
            onClick = onExplore,
            variant = ComicButtonVariant.Secondary,
        )
    }
}

private fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp,
    strokeWidth: Dp = Dimens.Border.Standard,
): Modifier = this.drawBehind {
    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f),
    )
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = stroke,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, heightDp = 800, widthDp = 360)
@Composable
private fun WishlistScreenPreview() {
    ComicBookRentalTheme {
        WishlistScreen(
            comics = listOf(
                Comic(
                    id = 1, title = "Ghost Runner #1", coverImageUrl = "", genres = listOf("Action"),
                    author = "Jax Vane", publisher = "Titan", description = "",
                    avgRating = "4.9", rentalPrice = 1.99, releaseDate = "2026",
                ),
                Comic(
                    id = 2, title = "Neon Noir", coverImageUrl = "", genres = listOf("Noir"),
                    author = "K. Murdock", publisher = "Titan", description = "",
                    avgRating = "5.0", rentalPrice = 2.49, releaseDate = "2026",
                ),
            ),
            onBack = {},
        )
    }
}
