package com.example.comicbookrental.data.repositories.auth

import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository
{
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit>
    {
        delay(AuthMockData.NETWORK_DELAY)

        return try
        {
            when
            {
                email == AuthMockData.ERROR_EMAIL -> throw AuthMockData.SERVER_ERROR
                email == AuthMockData.VALID_EMAIL && password == AuthMockData.VALID_PASSWORD -> Result.success(
                    Unit
                )

                else -> throw AuthMockData.CREDENTIAL_ERROR
            }
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }

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
            when
            {
                email == AuthMockData.ERROR_EMAIL -> throw AuthMockData.SERVER_ERROR
                email == AuthMockData.EXISTING_EMAIL -> throw AuthMockData.EMAIL_EXIST_ERROR
                else -> Result.success(Unit)
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
            when
            {
                email == AuthMockData.ERROR_EMAIL -> throw AuthMockData.SERVER_ERROR
                email == AuthMockData.VALID_EMAIL || email == AuthMockData.EXISTING_EMAIL -> Result.success(Unit)
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
                AuthMockData.VALID_OTP -> Result.success(Unit)
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
            Result.success(Unit)
        } catch (e: Exception)
        {
            Result.failure(e)
        }
    }
}