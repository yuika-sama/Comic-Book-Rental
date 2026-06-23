package com.example.comicbookrental.data.repositories.comic

import android.content.Context
import com.example.comicbookrental.data.dto.ComicDto
import com.example.comicbookrental.data.dto.toEntity
import com.example.comicbookrental.data.dto.toReviewEntities
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ComicRepository {

    private val json = Json { ignoreUnknownKeys = true }

    private val comicsFlow = MutableStateFlow<List<ComicEntity>>(emptyList())
    private val reviewsFlow = MutableStateFlow<List<ReviewEntity>>(emptyList())

    init {
        loadFromAssets()
    }

    /** Đọc comics.json trong assets, tách thành comics + reviews. */
    private fun loadFromAssets() {
        val text = context.assets.open("comics.json")
            .bufferedReader()
            .use { it.readText() }
        val dtos = json.decodeFromString<List<ComicDto>>(text)
        comicsFlow.value = dtos.map { it.toEntity() }
        reviewsFlow.value = dtos.flatMap { it.toReviewEntities() }
    }

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
            loadFromAssets()
        }
    }
}
