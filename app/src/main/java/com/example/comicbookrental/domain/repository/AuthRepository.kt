package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.UserRole

interface AuthRepository
{
    suspend fun login(email: String, password: String, rememberMe: Boolean = false): Result<Boolean>

    fun getCurrentRole(): UserRole

    suspend fun oAuthLogin(): Result<Unit>

    suspend fun register(name: String, email: String, password: String): Result<Unit>

    suspend fun sendOtp(email: String): Result<Unit>

    suspend fun verifyOtp(email: String, otp: String): Result<Unit>

    suspend fun resetPassword(email: String, newPassword: String): Result<Unit>
}