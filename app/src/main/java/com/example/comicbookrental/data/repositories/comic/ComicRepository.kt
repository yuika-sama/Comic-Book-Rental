package com.example.comicbookrental.data.repositories.comic

import com.example.comicbookrental.data.models.Comic
import com.example.comicbookrental.data.models.Review
import kotlinx.coroutines.flow.Flow

interface ComicRepository {
    fun getAllComics(): Flow<List<Comic>>
    fun searchComics(query: String): Flow<List<Comic>>
    fun getComicById(comicId: Int): Flow<Comic?>
    fun getReviewsForComic(comicId: Int): Flow<List<Review>>

    // Home sections
    fun getFeaturedComics(): Flow<List<Comic>>
    fun getNewReleases(limit: Int = 10): Flow<List<Comic>>
    fun getTopRated(limit: Int = 10): Flow<List<Comic>>
    fun getGenres(): Flow<List<String>>

    suspend fun insertComics(comics: List<Comic>)

    suspend fun seedIfEmpty()
}
