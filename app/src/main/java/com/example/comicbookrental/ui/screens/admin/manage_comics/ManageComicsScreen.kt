package com.example.comicbookrental.ui.screens.admin.manage_comics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.comicbookrental.ui.components.adminComponents.AdminComicCard
import com.example.comicbookrental.ui.components.adminComponents.ComicFormDialog
import com.example.comicbookrental.ui.components.adminComponents.DeleteComicDialog
import com.example.comicbookrental.ui.components.commonComponents.BrutalistButton
import com.example.comicbookrental.ui.components.commonComponents.BrutalistTextField
import com.example.comicbookrental.ui.theme.Dimens

@Composable
fun ManageComicsScreen(
    modifier: Modifier = Modifier,
    viewModel: ManageComicsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val editor by viewModel.editor.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.Spacing.ScreenPadding)
    ) {
        BrutalistButton(
            text = "Add comic",
            onClick = viewModel::onAddClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.ContentSpacing))

        BrutalistTextField(
            value = editor.searchQuery,
            onValueChange = viewModel::onSearchChange,
            label = "Search comics",
            placeholder = "Title or author...",
            leadingIcon = Icons.Default.Search
        )

        Spacer(modifier = Modifier.height(Dimens.Spacing.SectionSpacing))

        when (uiState) {
            is ManageComicsUiState.Loading -> LoadingState()

            is ManageComicsUiState.Error -> ErrorState(
                message = (uiState as ManageComicsUiState.Error).message
            )

            is ManageComicsUiState.Content -> {
                val comics = (uiState as ManageComicsUiState.Content).comics
                if (comics.isEmpty()) {
                    EmptyState(isSearching = editor.searchQuery.isNotBlank())
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.Gutter),
                        contentPadding = PaddingValues(bottom = Dimens.Spacing.SectionSpacing)
                    ) {
                        items(comics, key = { it.id }) { comic ->
                            AdminComicCard(
                                comic = comic,
                                onEdit = { viewModel.onEditClick(comic) },
                                onDelete = { viewModel.onDeleteClick(comic) }
                            )
                        }
                    }
                }
            }
        }
    }

    editor.form?.let { form ->
        ComicFormDialog(
            form = form,
            onChange = viewModel::onFormChange,
            onSave = viewModel::onFormSave,
            onDismiss = viewModel::onFormDismiss
        )
    }

    editor.comicPendingDelete?.let { comic ->
        DeleteComicDialog(
            comicTitle = comic.title,
            onConfirm = viewModel::onDeleteConfirm,
            onDismiss = viewModel::onDeleteDismiss
        )
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyState(isSearching: Boolean) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = if (isSearching)
                "No comics match your search."
            else
                "No comics yet. Tap \"Add comic\" to get started.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
