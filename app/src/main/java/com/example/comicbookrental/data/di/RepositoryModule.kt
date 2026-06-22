package com.example.comicbookrental.data.di
import com.example.comicbookrental.data.repositories.rental.RentalRepository
import com.example.comicbookrental.data.repositories.rental.RentalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRentalRepository(
        impl: RentalRepositoryImpl
    ): RentalRepository
}