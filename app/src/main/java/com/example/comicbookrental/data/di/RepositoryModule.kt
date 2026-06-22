package com.example.comicbookrental.data.di

import com.example.comicbookrental.data.repositories.user.UserRepository
import com.example.comicbookrental.data.repositories.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ) : UserRepository
}