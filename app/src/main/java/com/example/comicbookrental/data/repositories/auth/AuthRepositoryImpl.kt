package com.example.comicbookrental.data.repositories.auth

import com.example.comicbookrental.data.entities.UserRole
import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.domain.repository.AuthRepository
import com.example.comicbookrental.utils.StoreManager
import kotlinx.coroutines.delay
import okhttp3.internal.trimSubstring
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val storeManager: StoreManager
) : AuthRepository
{
    override suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Result<Boolean>
    {
        delay(AuthMockData.NETWORK_DELAY)

        return try
        {
            val credentials = storeManager.getUsersCredentials()
            val savedPassword = credentials[email.trim()]
            val isAdmin = email.trim() in AuthMockData.ADMIN_EMAILS

            when
            {
                email == AuthMockData.ERROR_EMAIL -> throw AuthMockData.SERVER_ERROR
                savedPassword != null && savedPassword == password -> {
                    if (!isAdmin && email.trim() in storeManager.getBannedUserEmails()) {
                        throw AuthMockData.BANNED_ERROR
                    }
                    val currentProfile = storeManager.getUserProfile()
                    val isVerified = isAdmin ||
                        (if (currentProfile.email == email) currentProfile.isEmailVerified else false)
                    val role = if (isAdmin) UserRole.ADMIN else UserRole.USER
                    storeManager.saveUserProfile(
                        currentProfile.copy(
                            email = email.trim(),
                            isEmailVerified = isVerified,
                            role = role
                        )
                    )
                    if (rememberMe){
                        storeManager.setLoggedIn(true)
                    }
                    Result.success(isVerified)
                }
                else -> throw AuthMockData.CREDENTIAL_ERROR
            }
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    override fun getCurrentRole(): UserRole = storeManager.getUserProfile().role

    override suspend fun oAuthLogin(): Result<Unit>
    {
        delay(AuthMockData.NETWORK_DELAY)

        return try
        {
            Result.success(Unit)
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit>
    {
        delay(AuthMockData.NETWORK_DELAY)
        return try
        {
            val credentials = storeManager.getUsersCredentials()
            when
            {
                email == AuthMockData.ERROR_EMAIL -> throw AuthMockData.SERVER_ERROR
                credentials.containsKey(email.trim()) -> throw AuthMockData.EMAIL_EXIST_ERROR
                else ->
                {
                    val updatedCredentials = credentials.toMutableMap()
                    updatedCredentials[email.trim()] = password
                    storeManager.saveUsersCredentials(updatedCredentials)

                    val profile = storeManager.getUserProfile()
                    storeManager.saveUserProfile(
                        profile.copy(
                            email = email,
                            realName = name,
                            heroName = name.uppercase(),
                            phone = "",
                            region = "",
                            isEmailVerified = false
                        )
                    )

                    Result.success(Unit)
                }
            }
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    override suspend fun sendOtp(email: String): Result<Unit>
    {
        delay(AuthMockData.NETWORK_DELAY)
        return try
        {
            val credentials = storeManager.getUsersCredentials()
            when
            {
                email == AuthMockData.ERROR_EMAIL -> throw AuthMockData.SERVER_ERROR
                credentials.containsKey(email.trim()) -> Result.success(Unit)
                else -> throw AuthMockData.EMAIL_NOT_FOUND_ERROR
            }
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): Result<Unit>
    {
        delay(AuthMockData.NETWORK_DELAY)
        return try
        {
            when (otp)
            {
                AuthMockData.ERROR_OTP -> throw AuthMockData.SERVER_ERROR
                AuthMockData.EXPIRED_OTP -> throw AuthMockData.OTP_EXPIRED_ERROR
                AuthMockData.VALID_OTP -> {
                    val currentProfile = storeManager.getUserProfile()
                    if (currentProfile.email == email){
                        storeManager.saveUserProfile(
                            currentProfile.copy(isEmailVerified = true)
                        )
                    }

                    Result.success(Unit)
                }
                else -> throw AuthMockData.INVALID_OTP_ERROR
            }
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(email: String, newPassword: String): Result<Unit>
    {
        delay(AuthMockData.NETWORK_DELAY)
        return try
        {
            if (newPassword == "error12345")
            {
                throw AuthMockData.SERVER_ERROR
            }
            val credentials = storeManager.getUsersCredentials()
            val updatedCredentials = credentials.toMutableMap()
            updatedCredentials[email.trim()] = newPassword
            storeManager.saveUsersCredentials(updatedCredentials)
            Result.success(Unit)
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }
}