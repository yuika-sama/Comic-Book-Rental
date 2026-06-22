package com.example.comicbookrental.data.repositories

import com.example.comicbookrental.data.repositories.comic.ComicRepository
import com.example.comicbookrental.data.repositories.comic.ComicRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule
{
    @Binds
    abstract fun bindComicRepository(impl: ComicRepositoryImpl): ComicRepository
}