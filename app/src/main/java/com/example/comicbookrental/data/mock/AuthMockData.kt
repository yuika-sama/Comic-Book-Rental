package com.example.comicbookrental.data.mock

object AuthMockData
{
    const val VALID_EMAIL = "comic.rent@gmail.com"
    const val VALID_PASSWORD = "12345678"
    const val ERROR_EMAIL = "error@test.com"
    const val NETWORK_DELAY = 1500L
    const val EXISTING_EMAIL = "exist@gmail.com"

    const val ADMIN_EMAIL = "admin@panelrush.com"
    const val ADMIN_PASSWORD = "12345678"
    val ADMIN_EMAILS = setOf(ADMIN_EMAIL)

    private const val DEMO_PASSWORD = "12345678"

    val SEED_USER_CREDENTIALS: Map<String, String> = mapOf(
        "namthegioi65@gmail.com" to "12345678",
        ADMIN_EMAIL to ADMIN_PASSWORD,
        "peter.parker@dailybugle.com" to DEMO_PASSWORD,
        "bruce.wayne@wayne.enterprises" to DEMO_PASSWORD,
        "clark.kent@dailyplanet.com" to DEMO_PASSWORD,
        "diana.prince@themyscira.gov" to DEMO_PASSWORD,
        "tony.stark@starkindustries.com" to DEMO_PASSWORD,
        "natasha.romanoff@shield.org" to DEMO_PASSWORD,
        "wade.wilson@maximumeffort.com" to DEMO_PASSWORD,
        "gwen.stacy@empirestate.edu" to DEMO_PASSWORD,
    )

    val SEED_BANNED_EMAILS: Set<String> = setOf("wade.wilson@maximumeffort.com")

    val SERVER_ERROR = Exception("500 - Internal Server Error")
    val CREDENTIAL_ERROR = IllegalArgumentException("Wrong email or password")
    val EMAIL_EXIST_ERROR =
        IllegalArgumentException("Email already exists. Please use a different email.")
    val EMAIL_NOT_FOUND_ERROR =
        IllegalArgumentException("No account found with this email address.")
    val BANNED_ERROR =
        IllegalArgumentException("This account has been banned. Please contact support.")

    const val VALID_OTP = "12345"
    const val EXPIRED_OTP = "88888"
    const val ERROR_OTP = "99999"

    val INVALID_OTP_ERROR = IllegalArgumentException("Invalid OTP. Please check the code and try again.")
    val OTP_EXPIRED_ERROR = IllegalArgumentException("OTP has expired. Please request a new code.")
}