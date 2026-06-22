package com.example.comicbookrental.data.repositories.comic

import com.example.comicbookrental.data.dao.ComicDao
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val dao: ComicDao,
) : ComicRepository {

    override fun getAllComics(): Flow<List<ComicEntity>> = dao.getAllComics()

    override fun searchComics(query: String): Flow<List<ComicEntity>> = dao.searchComics(query)

    override fun getComicById(comicId: Int): Flow<ComicEntity?> = dao.getComicById(comicId)

    override fun getReviewsForComic(comicId: Int): Flow<List<ReviewEntity>> =
        dao.getReviewsForComic(comicId)

    override fun getFeaturedComics(): Flow<List<ComicEntity>> = dao.getFeaturedComics()

    override fun getNewReleases(limit: Int): Flow<List<ComicEntity>> = dao.getNewReleases(limit)

    override fun getTopRated(limit: Int): Flow<List<ComicEntity>> = dao.getTopRated(limit)

    override fun getGenres(): Flow<List<String>> = dao.getGenres()

    override suspend fun insertComics(comics: List<ComicEntity>) = dao.insertComics(comics)

    override suspend fun seedIfEmpty() {
        // Peek the current catalog once; only seed when there's nothing there yet.
        val existing = dao.getAllComics().first()
        if (existing.isEmpty()) {
            dao.insertComics(SampleComics.list)
        }
    }
}
