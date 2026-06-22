package com.example.comicbookrental.data.repositories.comic

import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepositoryImpl @Inject constructor() : ComicRepository {

    private val comicsFlow = MutableStateFlow<List<ComicEntity>>(SampleComics.list)
    private val reviewsFlow = MutableStateFlow<List<ReviewEntity>>(emptyList())

    override fun getAllComics(): Flow<List<ComicEntity>> = comicsFlow

    override fun searchComics(query: String): Flow<List<ComicEntity>> {
        return comicsFlow.map { list ->
            list.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.author.contains(query, ignoreCase = true)
            }
        }
    }

    override fun getComicById(comicId: Int): Flow<ComicEntity?> {
        return comicsFlow.map { list ->
            list.find { it.id == comicId }
        }
    }

    override fun getReviewsForComic(comicId: Int): Flow<List<ReviewEntity>> {
        return reviewsFlow.map { list ->
            list.filter { it.comicId == comicId }
                .sortedByDescending { it.commentDate }
        }
    }

    override fun getFeaturedComics(): Flow<List<ComicEntity>> {
        return comicsFlow.map { list ->
            list.filter { it.isFeatured }
                .sortedByDescending { it.viewCount }
        }
    }

    override fun getNewReleases(limit: Int): Flow<List<ComicEntity>> {
        return comicsFlow.map { list ->
            list.sortedByDescending { it.releaseDate }
                .take(limit)
        }
    }

    override fun getTopRated(limit: Int): Flow<List<ComicEntity>> {
        return comicsFlow.map { list ->
            list.sortedByDescending { it.avgRating.toDoubleOrNull() ?: 0.0 }
                .take(limit)
        }
    }

    override fun getGenres(): Flow<List<String>> {
        return comicsFlow.map { list ->
            list.map { it.genre }
                .distinct()
                .sorted()
        }
    }

    override suspend fun insertComics(comics: List<ComicEntity>) {
        comicsFlow.value = (comicsFlow.value + comics).distinctBy { it.id }
    }

    override suspend fun seedIfEmpty() {
        if (comicsFlow.value.isEmpty()) {
            comicsFlow.value = SampleComics.list
        }
    }
}
