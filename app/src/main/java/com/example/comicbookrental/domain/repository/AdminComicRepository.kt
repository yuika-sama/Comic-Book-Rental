package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.Comic
import kotlinx.coroutines.flow.Flow

interface AdminComicRepository
{
    fun getComics(): Flow<List<Comic>>

    suspend fun seedIfEmpty()

    suspend fun addComic(comic: Comic)

    suspend fun updateComic(comic: Comic)

    suspend fun deleteComic(comicId: Int)

    fun nextId(): Int
}
