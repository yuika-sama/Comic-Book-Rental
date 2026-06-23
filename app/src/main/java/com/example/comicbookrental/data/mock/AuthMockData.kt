package com.example.comicbookrental.data.mock

object AuthMockData
{
    const val VALID_EMAIL = "comic.rent@gmail.com"
    const val VALID_PASSWORD = "12345678"
    const val ERROR_EMAIL = "error@test.com"
    const val NETWORK_DELAY = 1500L
    const val EXISTING_EMAIL = "exist@gmail.com"

    val SERVER_ERROR = Exception("500 - Internal Server Error")
    val CREDENTIAL_ERROR = IllegalArgumentException("Wrong email or password")
    val EMAIL_EXIST_ERROR =
        IllegalArgumentException("Email already exists. Please use a different email.")
}