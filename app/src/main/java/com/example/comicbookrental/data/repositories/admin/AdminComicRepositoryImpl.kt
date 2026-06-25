package com.example.comicbookrental.data.repositories.admin

import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.domain.repository.AdminComicRepository
import com.example.comicbookrental.domain.repository.ComicRepository
import com.example.comicbookrental.services.StorageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminComicRepositoryImpl @Inject constructor(
    private val storageManager: StorageManager,
    private val comicRepository: ComicRepository,
) : AdminComicRepository
{
    private val comicsFlow = MutableStateFlow(storageManager.getAdminComics())

    override fun getComics(): Flow<List<Comic>> = comicsFlow

    override suspend fun seedIfEmpty() {
        if (comicsFlow.value.isEmpty()) {
            val catalog = comicRepository.getAllComics().first()
            persist(catalog)
        }
    }

    override suspend fun addComic(comic: Comic) {
        persist(comicsFlow.value + comic)
    }

    override suspend fun updateComic(comic: Comic) {
        persist(comicsFlow.value.map { if (it.id == comic.id) comic else it })
    }

    override suspend fun deleteComic(comicId: Int) {
        persist(comicsFlow.value.filterNot { it.id == comicId })
    }

    override fun nextId(): Int = (comicsFlow.value.maxOfOrNull { it.id } ?: 0) + 1

    private fun persist(comics: List<Comic>) {
        comicsFlow.value = comics
        storageManager.saveAdminComics(comics)
    }
}
