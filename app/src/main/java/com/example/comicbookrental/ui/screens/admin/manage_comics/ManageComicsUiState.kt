package com.example.comicbookrental.ui.screens.admin.manage_comics

import com.example.comicbookrental.data.entities.Comic

sealed interface ManageComicsUiState {
    data object Loading : ManageComicsUiState

    data class Content(val comics: List<Comic>) : ManageComicsUiState

    data class Error(val message: String) : ManageComicsUiState
}

data class ManageComicsEditorState(
    val searchQuery: String = "",
    val form: ComicFormState? = null,
    val comicPendingDelete: Comic? = null,
)

data class ComicFormState(
    val editingId: Int? = null,
    val title: String = "",
    val author: String = "",
    val genre: String = "",
    val publisher: String = "",
    val description: String = "",
    val coverImageUrl: String = "",
    val rentalPrice: String = "",
    val releaseDate: String = "",
    val latestChapter: String = "1",
    val error: String = "",
) {
    val isEditing: Boolean get() = editingId != null
}

fun Comic.toFormState(): ComicFormState = ComicFormState(
    editingId = id,
    title = title,
    author = author,
    genre = genres.joinToString(", "),
    publisher = publisher,
    description = description,
    coverImageUrl = coverImageUrl,
    rentalPrice = if (rentalPrice % 1.0 == 0.0) rentalPrice.toLong().toString() else rentalPrice.toString(),
    releaseDate = releaseDate,
    latestChapter = latestChapter.toString(),
)
