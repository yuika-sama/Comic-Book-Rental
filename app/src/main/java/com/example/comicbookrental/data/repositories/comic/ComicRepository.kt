package com.example.comicbookrental.data.repositories.comic

import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow

/**
 * Single source of truth for comic data, consumed by ViewModels.
 *
 * Reads return cold [Flow]s straight from Room, so the UI re-renders automatically whenever
 * the underlying tables change. Writes are `suspend` (run off the main thread).
 */
interface ComicRepository {
    fun getAllComics(): Flow<List<ComicEntity>>
    fun searchComics(query: String): Flow<List<ComicEntity>>
    fun getComicById(comicId: Int): Flow<ComicEntity?>
    fun getReviewsForComic(comicId: Int): Flow<List<ReviewEntity>>

    // Home sections
    fun getFeaturedComics(): Flow<List<ComicEntity>>
    fun getNewReleases(limit: Int = 10): Flow<List<ComicEntity>>
    fun getTopRated(limit: Int = 10): Flow<List<ComicEntity>>
    fun getGenres(): Flow<List<String>>

    suspend fun insertComics(comics: List<ComicEntity>)

    /** Seeds the catalog with sample data the first time the table is empty (dev helper). */
    suspend fun seedIfEmpty()
}
