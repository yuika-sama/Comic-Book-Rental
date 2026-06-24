package com.example.comicbookrental.data.repositories.comic

import android.content.Context
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.data.entities.Review
import com.example.comicbookrental.domain.repository.ComicRepository
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
) : ComicRepository
{

    private val json = Json { ignoreUnknownKeys = true }

    private val comicsFlow = MutableStateFlow<List<Comic>>(emptyList())

    init {
        loadFromAssets()
    }

    /** Đọc comics.json trong assets (reviews nằm lồng trong từng comic). */
    private fun loadFromAssets() {
        val text = context.assets.open("comics.json")
            .bufferedReader()
            .use { it.readText() }
        comicsFlow.value = json.decodeFromString<List<Comic>>(text)
    }

    override fun getAllComics(): Flow<List<Comic>> = comicsFlow

    override fun searchComics(query: String): Flow<List<Comic>> {
        return comicsFlow.map { list ->
            list.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.author.contains(query, ignoreCase = true)
            }
        }
    }

    override fun getComicById(comicId: Int): Flow<Comic?> {
        return comicsFlow.map { list ->
            list.find { it.id == comicId }
        }
    }

    override fun getReviewsForComic(comicId: Int): Flow<List<Review>> {
        return comicsFlow.map { list ->
            list.find { it.id == comicId }
                ?.reviews
                ?.sortedByDescending { it.commentDate }
                ?: emptyList()
        }
    }

    override fun getFeaturedComics(): Flow<List<Comic>> {
        return comicsFlow.map { list ->
            list.filter { it.isFeatured }
                .sortedByDescending { it.viewCount }
        }
    }

    override fun getNewReleases(limit: Int): Flow<List<Comic>> {
        return comicsFlow.map { list ->
            list.sortedByDescending { it.releaseDate }
                .take(limit)
        }
    }

    override fun getTopRated(limit: Int): Flow<List<Comic>> {
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

    override suspend fun insertComics(comics: List<Comic>) {
        comicsFlow.value = (comicsFlow.value + comics).distinctBy { it.id }
    }

    override suspend fun seedIfEmpty() {
        if (comicsFlow.value.isEmpty()) {
            loadFromAssets()
        }
    }
}
