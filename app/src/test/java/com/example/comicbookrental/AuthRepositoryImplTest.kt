package com.example.comicbookrental

import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.data.repositories.auth.AuthRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthRepositoryImplTest {

    private val repository = AuthRepositoryImpl()

    @Test
    fun sendOtp_withValidEmail_returnsSuccess() = runBlocking {
        val result = repository.sendOtp(AuthMockData.VALID_EMAIL)
        assertTrue(result.isSuccess)
    }

    @Test
    fun sendOtp_withExistingEmail_returnsSuccess() = runBlocking {
        val result = repository.sendOtp(AuthMockData.EXISTING_EMAIL)
        assertTrue(result.isSuccess)
    }

    @Test
    fun sendOtp_withErrorEmail_returnsFailureWithServerError() = runBlocking {
        val result = repository.sendOtp(AuthMockData.ERROR_EMAIL)
        assertTrue(result.isFailure)
        assertEquals(AuthMockData.SERVER_ERROR, result.exceptionOrNull())
    }

    @Test
    fun sendOtp_withUnregisteredEmail_returnsFailureWithEmailNotFoundError() = runBlocking {
        val result = repository.sendOtp("unregistered@test.com")
        assertTrue(result.isFailure)
        assertEquals(AuthMockData.EMAIL_NOT_FOUND_ERROR, result.exceptionOrNull())
    }

    @Test
    fun verifyOtp_withValidOtp_returnsSuccess() = runBlocking {
        val result = repository.verifyOtp(AuthMockData.VALID_EMAIL, AuthMockData.VALID_OTP)
        assertTrue(result.isSuccess)
    }

    @Test
    fun verifyOtp_withErrorOtp_returnsFailureWithServerError() = runBlocking {
        val result = repository.verifyOtp(AuthMockData.VALID_EMAIL, AuthMockData.ERROR_OTP)
        assertTrue(result.isFailure)
        assertEquals(AuthMockData.SERVER_ERROR, result.exceptionOrNull())
    }

    @Test
    fun verifyOtp_withExpiredOtp_returnsFailureWithExpiredError() = runBlocking {
        val result = repository.verifyOtp(AuthMockData.VALID_EMAIL, AuthMockData.EXPIRED_OTP)
        assertTrue(result.isFailure)
        assertEquals(AuthMockData.OTP_EXPIRED_ERROR, result.exceptionOrNull())
    }

    @Test
    fun verifyOtp_withInvalidOtp_returnsFailureWithInvalidError() = runBlocking {
        val result = repository.verifyOtp(AuthMockData.VALID_EMAIL, "00000")
        assertTrue(result.isFailure)
        assertEquals(AuthMockData.INVALID_OTP_ERROR, result.exceptionOrNull())
    }
}
