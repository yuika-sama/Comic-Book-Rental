package com.example.comicbookrental.data.repositories.profile

import com.example.comicbookrental.data.entities.User
import com.example.comicbookrental.data.mock.ProfileMockData
import com.example.comicbookrental.domain.repository.ProfileRepository
import com.example.comicbookrental.services.StorageManager
import kotlinx.coroutines.delay
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val storageManager: StorageManager
) : ProfileRepository {

    override suspend fun getProfile(): Result<User> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            Result.success(storageManager.getUserProfile())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateAvatar(avatarUrl: String): Result<Unit>
    {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            if (avatarUrl.isNullOrBlank()){
                throw IllegalArgumentException("Avatar URL cannot be empty")
            }
            Result.success(storageManager.saveUserAvatar(avatarUrl))
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(
        heroName: String,
        realName: String,
        phone: String,
        region: String
    ): Result<User> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            if (heroName.isBlank()) {
                throw IllegalArgumentException("Hero name cannot be empty")
            }
            if (realName.isBlank()) {
                throw IllegalArgumentException("Name cannot be empty")
            }
            if (phone.isBlank()) {
                throw IllegalArgumentException("Phone number cannot be empty")
            }
            if (region.isBlank()) {
                throw IllegalArgumentException("Region/Sector cannot be empty")
            }

            val currentProfile = storageManager.getUserProfile()
            val updatedProfile = currentProfile.copy(
                heroName = heroName,
                realName = realName,
                phone = phone,
                region = region
            )

            storageManager.saveUserProfile(updatedProfile)

            Result.success(updatedProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit> {
        delay(ProfileMockData.NETWORK_DELAY)
        return try {
            val profile = storageManager.getUserProfile()
            val credentials = storageManager.getUsersCredentials()
            val currentSavedPassword = credentials[profile.email] ?: "12345678"

            if (oldPassword != "12345678") {
                throw IllegalArgumentException("Incorrect current password")
            }
            if (newPassword.length < 8) {
                throw IllegalArgumentException("New password must be at least 8 characters")
            }
            val updatedCredentials = credentials.toMutableMap()
            updatedCredentials[profile.email] = newPassword
            storageManager.saveUsersCredentials(updatedCredentials)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logOut()
    {
        storageManager.logOut()
    }
}
