package com.example.comicbookrental.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.CartComicCover
import com.example.comicbookrental.ui.components.detailComponents.ComicTitleBlock
import com.example.comicbookrental.ui.components.ComicTopBar
import com.example.comicbookrental.ui.components.detailComponents.HazardBanner
import com.example.comicbookrental.ui.components.detailComponents.HeroCoverFrame
import com.example.comicbookrental.ui.components.detailComponents.RentOptionCard
import com.example.comicbookrental.ui.components.detailComponents.ReviewUi
import com.example.comicbookrental.ui.components.detailComponents.ReviewsSection
import com.example.comicbookrental.ui.components.SectionHeader
import com.example.comicbookrental.ui.components.detailComponents.SimilarTitleUi
import com.example.comicbookrental.ui.components.detailComponents.SimilarTitlesSection
import com.example.comicbookrental.ui.components.detailComponents.SynopsisCard
import com.example.comicbookrental.ui.components.TopBarIconButton
import com.example.comicbookrental.ui.components.commonComponents.halftoneBackground
//import com.example.comicbookrental.ui.components.commonComponents.halftoneBackground
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens


/**
 * Detail entry point: collects [ComicDetailViewModel] state and renders the screen, with simple
 * loading / not-found / error fallbacks. [onComicClick] navigates to another comic's detail.
 */
@Composable
fun ComicDetailRoute(
    onBack: () -> Unit,
    onComicClick: (String) -> Unit,
    viewModel: ComicDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is ComicDetailUiState.Content -> ComicDetailScreen(
            state = state.detail,
            onBack = onBack,
            onSimilarClick = { onComicClick(it.id) },
        )

        ComicDetailUiState.Loading -> DetailStatus(onBack = onBack) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        ComicDetailUiState.NotFound -> DetailStatus(onBack = onBack) {
            StatusMessage("This comic isn't available.")
        }

        is ComicDetailUiState.Error -> DetailStatus(onBack = onBack) {
            StatusMessage(state.message)
        }
    }
}

@Composable
private fun DetailStatus(
    onBack: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        ComicTopBar(onBack = onBack)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.Spacing.StackLg),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}

@Composable
private fun StatusMessage(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}


@Composable
fun ComicDetailScreen(
    state: ComicDetailUi,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onBookmark: () -> Unit = {},
    onRent: (RentOptionUi) -> Unit = {},
    onAddToCart: (RentOptionUi) -> Unit = {},
    onDismissBonusNote: () -> Unit = {},
    onViewAllSimilar: () -> Unit = {},
    onSimilarClick: (SimilarTitleUi) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .halftoneBackground(),
    ) {
        ComicTopBar(
            onBack = onBack,
            title = state.title,
            actions = {
                TopBarIconButton(
                    icon = Icons.Filled.FavoriteBorder,
                    contentDescription = "Bookmark",
                    onClick = onBookmark,
                )
            },
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(
                top = Dimens.Spacing.ContentSpacing,
                bottom = Dimens.Spacing.SectionSpacing,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.SectionSpacing),
        ) {
            item {
                HeroCoverFrame(
                    badgeText = state.badgeText,
                    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
                    cover = {
                        CartComicCover(url = state.coverUrl, contentDescription = state.title)
                    },
                )
            }

            item {
                ComicTitleBlock(
                    title = state.title,
                    creators = state.creators,
                    publisher = state.publisher,
                    genres = state.genres,
                    rating = state.rating,
                    reviewCount = state.reviewCount,
                    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
                )
            }

            item {
                SynopsisCard(
                    text = state.synopsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.Spacing.Margin),
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
                    verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
                ) {
                    SectionHeader(title = "Rent Options", leadingIcon = Icons.Filled.ShoppingCart)
                    state.rentOptions.forEach { option ->
                        RentOptionCard(
                            title = option.title,
                            price = option.price,
                            subtitle = option.subtitle,
                            ctaText = option.ctaText,
                            onRent = { onRent(option) },
                            onAddToCart = { onAddToCart(option) },
                            highlighted = option.highlighted,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }

            if (state.bonusNote != null) {
                item {
                    HazardBanner(
                        message = state.bonusNote,
                        onDismiss = onDismissBonusNote,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.Spacing.Margin),
                    )
                }
            }

            item {
                ReviewsSection(
                    averageRating = state.rating,
                    reviewCount = state.reviewCount,
                    reviews = state.reviews,
                    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
                )
            }

            item {
                SimilarTitlesSection(
                    titles = state.similarTitles,
                    onViewAll = onViewAllSimilar,
                    onTitleClick = onSimilarClick,
                    modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, heightDp = 2200, widthDp = 360)
@Composable
private fun ComicDetailScreenPreview() {
    ComicBookRentalTheme {
        ComicDetailScreen(
            state = ComicDetailUi(
                title = "Cyber-Soul Revenant #42",
                creators = "J.J. Inkwell & The Neon Collective",
                publisher = "Titan Graphics Publishing",
                genres = listOf("Sci-Fi", "Noir"),
                rating = 4.8f,
                reviewCount = 1240,
                coverUrl = "",
                badgeText = "HOT PICK",
                synopsis = "In the rain-slicked canyons of Neo-Tokyo, a forgotten ghost in the " +
                    "machine wakes up with a single directive: justice. Issue #42 takes us deep " +
                    "into the mainframe as Revenant faces the Iron Shogun's digital enforcers.",
                rentOptions = listOf(
                    RentOptionUi("single", "Single Issue", "$1.99", "7 Days Access", "Rent Now"),
                    RentOptionUi(
                        id = "arc",
                        title = "Full Arc (S1)",
                        price = "$14.99",
                        subtitle = "Permanent Library",
                        ctaText = "Rent Season",
                        highlighted = true,
                    ),
                ),
                bonusNote = "Digital edition includes bonus art gallery.",
                reviews = listOf(
                    ReviewUi("Kenji R.", 5, "12 Jun 2026", "Absolutely electric. Best issue yet."),
                    ReviewUi("Mara V.", 4, "03 Jun 2026", "Great pacing, cliffhanger felt rushed."),
                ),
                similarTitles = listOf(
                    SimilarTitleUi("1", "Detective X: Ghost Protocol", "$1.50"),
                    SimilarTitleUi("2", "Mecha-Dragons #3", "$2.99"),
                ),
            ),
            onBack = {},
        )
    }
}
