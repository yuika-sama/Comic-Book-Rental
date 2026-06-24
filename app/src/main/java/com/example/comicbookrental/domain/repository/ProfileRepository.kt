package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.entities.User

interface ProfileRepository {
    suspend fun getProfile(): Result<User>
    suspend fun updateProfile(heroName: String, realName: String, phone: String, region: String): Result<User>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit>

    suspend fun updateAvatar(avatarUrl: String): Result<Unit>

    suspend fun logOut()
}
