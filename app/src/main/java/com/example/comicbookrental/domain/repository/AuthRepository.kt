package com.example.comicbookrental.domain.repository

interface AuthRepository
{
    suspend fun login(email: String, password: String): Result<Unit>

    suspend fun oAuthLogin(): Result<Unit>

    suspend fun register(name: String, email: String, password: String): Result<Unit>

    suspend fun sendOtp(email: String): Result<Unit>

    suspend fun verifyOtp(email: String, otp: String): Result<Unit>
}