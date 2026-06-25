package com.example.comicbookrental.ui.components.homeComponents
import com.example.comicbookrental.ui.components.commonComponents.GenreCard
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.comicbookrental.ui.theme.ComicBookRentalTheme
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors


@Composable
fun PopularGenresSection(
    genres: List<String>,
    onGenreClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedGenre: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.extendedColors.ink)
            .padding(vertical = Dimens.Spacing.SectionSpacing),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        SectionHeader(
            title = "Popular Genres",
            titleColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(horizontal = Dimens.Spacing.Margin),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = Dimens.Spacing.Margin),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackMd),
        ) {
            itemsIndexed(items = genres) { index, genre ->
                GenreCard(
                    label = genre,
                    accent = genreAccent(index),
                    filled = false,
                    selected = genre == selectedGenre,
                    onClick = { onGenreClick(genre) },
                )
            }
        }
    }
}

@Composable
private fun genreAccent(index: Int): Color = when (index % 3) {
    0 -> MaterialTheme.colorScheme.primary
    1 -> MaterialTheme.colorScheme.secondary
    else -> MaterialTheme.colorScheme.tertiary
}

@Preview(showBackground = true, backgroundColor = 0xFFFCF9F8, widthDp = 360)
@Composable
private fun PopularGenresSectionPreview() {
    ComicBookRentalTheme {
        PopularGenresSection(
            genres = listOf("Action", "Sci-Fi", "Noir", "Horror", "Fantasy"),
            onGenreClick = {},
        )
    }
}
