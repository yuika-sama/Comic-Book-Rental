package com.example.comicbookrental.data.repositories.profile

import com.example.comicbookrental.data.mock.ProfileMockData
import com.example.comicbookrental.data.models.UserProfile
import com.example.comicbookrental.domain.repository.ProfileRepository
import com.example.comicbookrental.utils.StoreManager
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val storeManager: StoreManager
) : ProfileRepository {

    override suspend fun getProfile(): Result<UserProfile> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            Result.success(storeManager.getUserProfile())
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

            val currentProfile = storeManager.getUserProfile()
            val updatedProfile = currentProfile.copy(
                realName = realName,
                phone = phone,
                region = region
            )

            storeManager.saveUserProfile(updatedProfile)

            Result.success(updatedProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            val profile = storeManager.getUserProfile()
            val credentials = storeManager.getUsersCredentials()
            val currentSavedPassword = credentials[profile.email] ?: "12345678"

            if (oldPassword != "12345678") {
                throw IllegalArgumentException("Incorrect current password")
            }
            if (newPassword.length < 8) {
                throw IllegalArgumentException("New password must be at least 8 characters")
            }
            val updatedCredentials = credentials.toMutableMap()
            updatedCredentials[profile.email] = newPassword
            storeManager.saveUsersCredentials(updatedCredentials)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
