package com.example.comicbookrental.ui.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDisplayDate(): String {
    return SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).format(Date(this))
}

fun Long.toVnd(): String {
    return NumberFormat
        .getNumberInstance(Locale("vi", "VN"))
        .format(this) + " ₫"
}