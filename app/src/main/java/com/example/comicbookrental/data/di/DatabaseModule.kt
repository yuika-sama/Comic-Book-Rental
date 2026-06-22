package com.example.comicbookrental.data.di

import android.content.Context
import androidx.room.Room
import com.example.comicbookrental.data.dao.ComicDao
import com.example.comicbookrental.data.dao.UserDao
import com.example.comicbookrental.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase
    {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "comic_rental_db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideUserDao(
        db: AppDatabase
    ): UserDao
    {
        return db.userDao
    }

    @Provides
    fun provideComicDao(
        db: AppDatabase
    ): ComicDao
    {
        return db.comicDao
    }

}