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

    @Query("SELECT * FROM reviews WHERE comicId = :comicId ORDER BY commentDate DESC")
    fun getReviewsForComic(comicId: Int): Flow<List<ReviewEntity>>
}