package com.example.comicbookrental.data.repositories.profile

import com.example.comicbookrental.data.mock.ProfileMockData
import com.example.comicbookrental.data.entities.UserProfile
import com.example.comicbookrental.domain.repository.ProfileRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {

    override suspend fun getProfile(): Result<UserProfile> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            Result.success(ProfileMockData.mockUserProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(
        realName: String,
        phone: String,
        region: String
    ): Result<UserProfile> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            if (realName.isBlank()) {
                throw IllegalArgumentException("Name cannot be empty")
            }
            if (phone.isBlank()) {
                throw IllegalArgumentException("Phone number cannot be empty")
            }
            if (region.isBlank()) {
                throw IllegalArgumentException("Region/Sector cannot be empty")
            }
            
            ProfileMockData.mockUserProfile = ProfileMockData.mockUserProfile.copy(
                realName = realName,
                phone = phone,
                region = region
            )
            Result.success(ProfileMockData.mockUserProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            if (oldPassword != "12345678") {
                throw IllegalArgumentException("Incorrect current password")
            }
            if (newPassword.length < 8) {
                throw IllegalArgumentException("New password must be at least 8 characters")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
