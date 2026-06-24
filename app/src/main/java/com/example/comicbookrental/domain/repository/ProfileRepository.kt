package com.example.comicbookrental.domain.repository

import com.example.comicbookrental.data.models.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): Result<UserProfile>
    suspend fun updateProfile(realName: String, phone: String, region: String): Result<UserProfile>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit>
}
