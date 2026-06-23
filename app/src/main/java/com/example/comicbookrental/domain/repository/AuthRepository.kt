package com.example.comicbookrental.domain.repository

interface AuthRepository
{
    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun oAuthLogin(): Result<Unit>

    suspend fun register(name: String, email: String, password: String): Result<Unit>
}