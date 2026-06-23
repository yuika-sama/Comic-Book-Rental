package com.example.comicbookrental.data.mock

object AuthMockData
{
    const val VALID_EMAIL = "comic.rent@gmail.com"
    const val VALID_PASSWORD = "12345678"
    const val ERROR_EMAIL = "error@test.com"
    const val NETWORK_DELAY = 1500L

    val SERVER_ERROR = Exception("Lỗi server 500.")
    val CREDENTIAL_ERROR = IllegalArgumentException("Sai tài khoản hoặc mật khẩu.")
}