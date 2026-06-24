package com.example.comicbookrental.data.mock

import com.example.comicbookrental.data.models.UserProfile

object ProfileMockData {
    const val NETWORK_DELAY = 1000L

    var mockUserProfile = UserProfile(
        id = "1",
        heroName = "YUIKA SAMA",
        realName = "Nam Thế Giới",
        email = "namthegioi65@gmail.com",
        rank = "HEROIC",
        phone = "+84 (09) 87 654 321",
        region = "Ha Noi",
        rentedCount = 128,
        activeCount = 14,
        rating = 4.9,
        avatarUrl = ""
    )
}
