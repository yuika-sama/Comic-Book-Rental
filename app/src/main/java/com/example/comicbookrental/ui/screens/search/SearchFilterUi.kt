package com.example.comicbookrental.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.commonComponents.GenreButton
import com.example.comicbookrental.ui.components.commonComponents.SectionHeader
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

private data class ActiveChip(val label: String, val onRemove: () -> Unit)


@Composable
fun FilterBar(
    filters: SearchFilters,
    onOpenSheet: () -> Unit,
    onFiltersChange: (SearchFilters) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
            ) {
                Icon(
                    imageVector = Icons.Filled.Tune,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(Dimens.Icon.Medium),
                )
                Text(
                    text = "Filters".uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            FilterTriggerButton(count = filters.activeCount, onClick = onOpenSheet)
        }

        val chips = filters.toActiveChips(onFiltersChange)
        if (chips.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
                verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
            ) {
                chips.forEach { chip ->
                    RemovableChip(label = chip.label, onRemove = chip.onRemove)
                }
            }
        }
    }
}

@Composable
private fun FilterTriggerButton(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Button)
    val ink = MaterialTheme.extendedColors.ink
    val active = count > 0
    Row(
        modifier = modifier
            .height(Dimens.Sizes.ButtonHeight)
            .clip(shape)
            .background(if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
            .border(Dimens.Border.Standard, ink, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = Dimens.Spacing.ContentSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
    ) {
        Icon(
            imageVector = Icons.Filled.Tune,
            contentDescription = null,
            tint = if (active) MaterialTheme.colorScheme.onPrimary else ink,
            modifier = Modifier.size(Dimens.Icon.Small),
        )
        Text(
            text = if (active) "Filter ($count)".uppercase() else "Filter".uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun RemovableChip(
    label: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(Dimens.Radius.Button)
    val ink = MaterialTheme.extendedColors.ink
    Row(
        modifier = modifier
            .height(Dimens.Sizes.ChipHeight)
            .clip(shape)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(Dimens.Border.Standard, ink, shape)
            .clickable(onClick = onRemove)
            .padding(start = Dimens.Spacing.ContentSpacing, end = Dimens.Spacing.StackSm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Remove $label",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(Dimens.Icon.Small),
        )
    }
}

private fun SearchFilters.toActiveChips(onChange: (SearchFilters) -> Unit): List<ActiveChip> = buildList {
    genres.forEach { g -> add(ActiveChip(g) { onChange(copy(genres = genres - g)) }) }
    authors.forEach { a -> add(ActiveChip(a) { onChange(copy(authors = authors - a)) }) }
    minRating?.let { r -> add(ActiveChip("${r}★+") { onChange(copy(minRating = null)) }) }
    years.forEach { y -> add(ActiveChip(y) { onChange(copy(years = years - y)) }) }
    if (popularOnly) add(ActiveChip("Popular") { onChange(copy(popularOnly = false)) })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheet(
    filters: SearchFilters,
    availableGenres: List<String>,
    availableAuthors: List<String>,
    availableYears: List<String>,
    onFiltersChange: (SearchFilters) -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Spacing.Margin)
                .padding(bottom = Dimens.Spacing.StackLg),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.SectionSpacing),
        ) {
            SectionHeader(
                title = "Filters",
                actionLabel = if (filters.isActive) "Clear All" else null,
                onActionClick = if (filters.isActive) onClear else null,
            )

            FilterSection(title = "Genre") {
                availableGenres.forEach { genre ->
                    ToggleChip(
                        label = genre,
                        selected = genre in filters.genres,
                        onToggle = {
                            val next = if (genre in filters.genres) filters.genres - genre else filters.genres + genre
                            onFiltersChange(filters.copy(genres = next))
                        },
                    )
                }
            }

            FilterSection(title = "Author") {
                availableAuthors.forEach { author ->
                    ToggleChip(
                        label = author,
                        selected = author in filters.authors,
                        onToggle = {
                            val next = if (author in filters.authors) filters.authors - author else filters.authors + author
                            onFiltersChange(filters.copy(authors = next))
                        },
                    )
                }
            }

            FilterSection(title = "Minimum Rating") {
                RATING_FILTER_OPTIONS.forEach { threshold ->
                    ToggleChip(
                        label = "${threshold}★+",
                        selected = filters.minRating == threshold,
                        onToggle = {
                            onFiltersChange(filters.copy(minRating = if (filters.minRating == threshold) null else threshold))
                        },
                    )
                }
            }

            FilterSection(title = "Release Year") {
                availableYears.forEach { year ->
                    ToggleChip(
                        label = year,
                        selected = year in filters.years,
                        onToggle = {
                            val next = if (year in filters.years) filters.years - year else filters.years + year
                            onFiltersChange(filters.copy(years = next))
                        },
                    )
                }
            }

            FilterSection(title = "Popularity") {
                ToggleChip(
                    label = "Popular Only",
                    selected = filters.popularOnly,
                    onToggle = { onFiltersChange(filters.copy(popularOnly = !filters.popularOnly)) },
                )
            }
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable FlowRowScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
            verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.StackSm),
            content = content,
        )
    }
}

/** Selectable filter chip — reuses [GenreButton], flipping its colors when selected. */
@Composable
private fun ToggleChip(
    label: String,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GenreButton(
        label = label,
        onClick = onToggle,
        modifier = modifier,
        containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    )
}
