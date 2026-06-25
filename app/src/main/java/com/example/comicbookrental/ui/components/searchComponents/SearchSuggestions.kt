package com.example.comicbookrental.ui.components.searchComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.comicbookrental.ui.components.authComponents.ComicCard
import com.example.comicbookrental.ui.screens.search.SearchSuggestion
import com.example.comicbookrental.ui.screens.search.SuggestionType
import com.example.comicbookrental.ui.theme.Dimens
import com.example.comicbookrental.ui.theme.extendedColors

@Composable
fun SearchSuggestions(
    suggestions: List<SearchSuggestion>,
    onSuggestionClick: (SearchSuggestion) -> Unit,
    modifier: Modifier = Modifier,
) {
    ComicCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        suggestions.forEachIndexed { index, suggestion ->
            if (index > 0) {
                HorizontalDivider(
                    thickness = Dimens.Border.Hairline,
                    color = MaterialTheme.extendedColors.ink.copy(alpha = 0.15f),
                )
            }
            val icon = when (suggestion.type) {
                SuggestionType.TITLE -> Icons.Filled.Search
                SuggestionType.AUTHOR -> Icons.Outlined.Person
                SuggestionType.GENRE -> Icons.Filled.Tune
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(suggestion) }
                    .padding(
                        horizontal = Dimens.Spacing.ContentSpacing,
                        vertical = Dimens.Spacing.ListItemSpacing,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.ContentSpacing),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(Dimens.Icon.Medium),
                )
                Text(
                    text = suggestion.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = suggestion.type.label.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
