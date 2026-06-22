package com.example.comicbookrental.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(comics: List<ComicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM comics")
    fun getAllComics(): Flow<List<ComicEntity>>

    @Query("SELECT * FROM comics WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%'")
    fun searchComics(query: String): Flow<List<ComicEntity>>

    @Query("SELECT * FROM comics WHERE id = :comicId")
    fun getComicById(comicId: Int): Flow<ComicEntity?>

    // --- Home sections ---

    /** Featured carousel: flagged comics, most-viewed first. */
    @Query("SELECT * FROM comics WHERE isFeatured = 1 ORDER BY viewCount DESC")
    fun getFeaturedComics(): Flow<List<ComicEntity>>

    /** New Releases row: newest by release date. */
    @Query("SELECT * FROM comics ORDER BY releaseDate DESC LIMIT :limit")
    fun getNewReleases(limit: Int): Flow<List<ComicEntity>>

    /** Top Rated Epics: highest rating first (CAST so "5.0" sorts above "4.9" numerically). */
    @Query("SELECT * FROM comics ORDER BY CAST(avgRating AS REAL) DESC LIMIT :limit")
    fun getTopRated(limit: Int): Flow<List<ComicEntity>>

    /** Popular Genres: distinct genre names present in the catalog. */
    @Query("SELECT DISTINCT genre FROM comics ORDER BY genre")
    fun getGenres(): Flow<List<String>>

    @Query("SELECT * FROM reviews WHERE comicId = :comicId ORDER BY commentDate DESC")
    fun getReviewsForComic(comicId: Int): Flow<List<ReviewEntity>>
}