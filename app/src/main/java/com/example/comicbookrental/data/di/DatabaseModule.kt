package com.example.comicbookrental.data.di

import android.content.Context
import androidx.room.Room
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
    ) : AppDatabase
    {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "comic_rental_db"
        )
            // need to write Migration queries if some schema change
            .fallbackToDestructiveMigration(false)
            .build()
    }

}