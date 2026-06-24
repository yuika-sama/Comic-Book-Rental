package com.example.comicbookrental.data.repositories

import com.example.comicbookrental.domain.repository.CartRepository
import com.example.comicbookrental.data.repositories.cart.CartRepositoryImpl
import com.example.comicbookrental.domain.repository.ComicRepository
import com.example.comicbookrental.data.repositories.comic.ComicRepositoryImpl
import com.example.comicbookrental.data.repositories.profile.ProfileRepositoryImpl
import com.example.comicbookrental.domain.repository.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import com.example.comicbookrental.domain.repository.NotificationRepository
import com.example.comicbookrental.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule
{
    @Binds
    abstract fun bindComicRepository(impl: ComicRepositoryImpl): ComicRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(impl: com.example.comicbookrental.data.repositories.notification.NotificationRepositoryImpl): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl) : CartRepository

    @Binds
    @Singleton
    abstract fun bindRentalRepository(impl: RentalRepositoryImpl): RentalRepository
}