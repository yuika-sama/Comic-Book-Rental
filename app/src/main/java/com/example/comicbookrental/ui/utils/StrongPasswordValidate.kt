package com.example.comicbookrental.ui.utils

fun isStrongPassword(password: String): Boolean{
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!\\-_*])(?=\\S+$).{8,}$".toRegex()
    return passwordPattern.matches(password)
}

fun isPhoneNumber(phone: String): Boolean {
    val phoneRegex = "^[0-9]{10}$".toRegex()
    return phoneRegex.matches(phone)
}