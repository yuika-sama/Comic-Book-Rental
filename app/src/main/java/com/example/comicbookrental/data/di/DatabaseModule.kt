package com.example.comicbookrental.data.di

import android.content.Context
import androidx.room.Room
import com.example.comicbookrental.data.dao.RentalDao
import com.example.comicbookrental.data.dao.UserDao
import com.example.comicbookrental.data.local.AppDatabase
import com.example.comicbookrental.data.repositories.rental.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
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
            // need to write Migration queries if some schema change
            .fallbackToDestructiveMigration(false)
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
    fun provideRentalDao(
        db: AppDatabase
    ): RentalDao {
        return db.rentalDao
    }
}