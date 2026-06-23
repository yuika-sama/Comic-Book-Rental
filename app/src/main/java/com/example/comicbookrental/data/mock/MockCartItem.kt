package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.models.CartItem
import com.example.comicbookrental.data.models.MILLIS_PER_DAY

object MockCartData {

    private val now = System.currentTimeMillis()

    val cartItems = mutableListOf(
        CartItem(
            comicId = 1,
            comicTitle = "Solo Leveling",
            comicAuthor = "Chugong",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAcXGr6r1HemXimBqcdQ2rIrAltN95YCLjwjQknzwnAA&s=10",
            pricePerDay = 15000L,
            startDate = now,
            endDate = now + 7 * MILLIS_PER_DAY
        ),

        CartItem(
            comicId = 2,
            comicTitle = "One Piece",
            comicAuthor = "Eiichiro Oda",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-g1PV_IrIiWZMhewfUosHJhLFjBx79XbJ0KSFQOxy-A&s",
            pricePerDay = 12000L,
            startDate = now + MILLIS_PER_DAY,
            endDate = now + 10 * MILLIS_PER_DAY
        ),

        CartItem(
            comicId = 3,
            comicTitle = "Chainsaw Man",
            comicAuthor = "Tatsuki Fujimoto",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFxxYZ8_Cbg5YN1uEo1-RfK1Uq16uNY4JJ3-i7bbqxqn4vZl-mpfC4VLg&s=10",
            pricePerDay = 18000L,
            startDate = now,
            endDate = now + 5 * MILLIS_PER_DAY
        ),

        CartItem(
            comicId = 4,
            comicTitle = "Jujutsu Kaisen",
            comicAuthor = "Gege Akutami",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSN9oXaxAdiY1Wqf0T_nCSZvSPRPtRJEuCAtKhbIHeECA&s=10",
            pricePerDay = 16000L,
            startDate = now + 2 * MILLIS_PER_DAY,
            endDate = now + 16 * MILLIS_PER_DAY
        ),

        CartItem(
            comicId = 5,
            comicTitle = "Demon Slayer",
            comicAuthor = "Koyoharu Gotouge",
            comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSy9N7h_bWwZKsWFz2SH_khCBFFCWd2c8dJ6k9Dy3r6Z9PaS0hlCXP117Hd&s=10",
            pricePerDay = 14000L,
            startDate = now,
            endDate = now + 30 * MILLIS_PER_DAY
        )
    )
}