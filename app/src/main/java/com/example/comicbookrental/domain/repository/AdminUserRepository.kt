package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.AdminUser
import kotlinx.coroutines.flow.Flow

interface AdminUserRepository
{
    fun getUsers(): Flow<List<AdminUser>>

    suspend fun banUser(email: String)

    suspend fun unbanUser(email: String)

    suspend fun refresh()
}
