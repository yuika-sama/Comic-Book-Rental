package com.example.comicbookrental.ui.screens.admin.manage_comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.domain.repository.AdminComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageComicsViewModel @Inject constructor(
    private val repository: AdminComicRepository
) : ViewModel()
{
    private val _editor = MutableStateFlow(ManageComicsEditorState())
    val editor: StateFlow<ManageComicsEditorState> = _editor.asStateFlow()

    val uiState: StateFlow<ManageComicsUiState> =
        combine(
            repository.getComics(),
            _editor.map { it.searchQuery }.distinctUntilChanged()
        ) { comics, query ->
            ManageComicsUiState.Content(applyFilter(comics, query)) as ManageComicsUiState
        }
            .catch { e -> emit(ManageComicsUiState.Error(e.message ?: "Couldn't load comics")) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ManageComicsUiState.Loading,
            )

    init {
        viewModelScope.launch { repository.seedIfEmpty() }
    }

    private fun applyFilter(list: List<Comic>, query: String): List<Comic> {
        val q = query.trim()
        if (q.isEmpty()) return list
        return list.filter {
            it.title.contains(q, ignoreCase = true) || it.author.contains(q, ignoreCase = true)
        }
    }

    fun onSearchChange(query: String) {
        _editor.update { it.copy(searchQuery = query) }
    }

    fun onAddClick() {
        _editor.update { it.copy(form = ComicFormState()) }
    }

    fun onEditClick(comic: Comic) {
        _editor.update { it.copy(form = comic.toFormState()) }
    }

    fun onFormChange(newForm: ComicFormState) {
        _editor.update { it.copy(form = newForm.copy(error = "")) }
    }

    fun onFormDismiss() {
        _editor.update { it.copy(form = null) }
    }

    fun onFormSave() {
        val form = _editor.value.form ?: return

        if (form.title.isBlank()) {
            _editor.update { it.copy(form = form.copy(error = "Title can't be empty")) }
            return
        }
        val price = form.rentalPrice.trim().toDoubleOrNull()
        if (price == null || price < 0) {
            _editor.update { it.copy(form = form.copy(error = "Invalid rental price")) }
            return
        }
        val chapter = form.latestChapter.trim().toIntOrNull()
        if (chapter == null || chapter < 0) {
            _editor.update { it.copy(form = form.copy(error = "Invalid chapter number")) }
            return
        }

        val original = form.editingId?.let { id -> currentComics().find { it.id == id } }
        val comic = Comic(
            id = form.editingId ?: repository.nextId(),
            title = form.title.trim(),
            coverImageUrl = form.coverImageUrl.trim(),
            genres = form.genre.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            author = form.author.trim(),
            publisher = form.publisher.trim(),
            description = form.description.trim(),
            avgRating = original?.avgRating ?: "0.0",
            rentalPrice = price,
            releaseDate = form.releaseDate.trim(),
            viewCount = original?.viewCount ?: 0,
            ratingsCount = original?.ratingsCount ?: 0,
            isFeatured = original?.isFeatured ?: false,
            latestChapter = chapter,
            reviews = original?.reviews ?: emptyList(),
        )

        viewModelScope.launch {
            if (form.isEditing) repository.updateComic(comic) else repository.addComic(comic)
            _editor.update { it.copy(form = null) }
        }
    }

    fun onDeleteClick(comic: Comic) {
        _editor.update { it.copy(comicPendingDelete = comic) }
    }

    fun onDeleteDismiss() {
        _editor.update { it.copy(comicPendingDelete = null) }
    }

    fun onDeleteConfirm() {
        val comic = _editor.value.comicPendingDelete ?: return
        viewModelScope.launch {
            repository.deleteComic(comic.id)
            _editor.update { it.copy(comicPendingDelete = null) }
        }
    }

    private fun currentComics(): List<Comic> =
        (uiState.value as? ManageComicsUiState.Content)?.comics ?: emptyList()
}
