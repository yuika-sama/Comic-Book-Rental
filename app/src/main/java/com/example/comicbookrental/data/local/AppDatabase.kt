package com.example.comicbookrental.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.comicbookrental.data.dao.ComicDao
import com.example.comicbookrental.data.dao.UserDao
import com.example.comicbookrental.data.entities.ComicEntity
import com.example.comicbookrental.data.entities.ReviewEntity
import com.example.comicbookrental.data.entities.User

/**
 * Room database for the app.
 *
 * version history: 1 = user only; 2 = added comics + reviews tables; 3 = added
 * viewCount/ratingsCount/isFeatured columns to comics. During development we rely on
 * destructive migration (see DatabaseModule) instead of hand-written migrations.
 */
@Database(
    entities = [
        User::class,
        ComicEntity::class,
        ReviewEntity::class,
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val comicDao: ComicDao
}
