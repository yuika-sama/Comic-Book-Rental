package com.example.comicbookrental.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.comicbookrental.data.entities.AppNotification
import com.example.comicbookrental.data.entities.CartItem
import com.example.comicbookrental.data.entities.Comic
import com.example.comicbookrental.data.entities.Rental
import com.example.comicbookrental.data.entities.RentalStatus
import com.example.comicbookrental.data.entities.User
import com.example.comicbookrental.data.mock.AuthMockData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class StoreManager @Inject constructor(
    @ApplicationContext private val context: Context
)
{
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "comic_book_rental_prefs",
        Context.MODE_PRIVATE
    )

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    init
    {
        if (!prefs.contains("cart_item"))
        {
            val now = System.currentTimeMillis()
            val initialCart = listOf(
                CartItem(
                    comicId = 1,
                    comicTitle = "Solo Leveling",
                    comicAuthor = "Chugong",
                    comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAcXGr6r1HemXimBqcdQ2rIrAltN95YCLjwjQknzwnAA&s=10",
                    pricePerDay = 2.99,
                    startDate = now,
                    endDate = now + 7 * 24 * 60 * 60 * 1000L
                ),
                CartItem(
                    comicId = 2,
                    comicTitle = "One Piece",
                    comicAuthor = "Eiichiro Oda",
                    comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-g1PV_IrIiWZMhewfUosHJhLFjBx79XbJ0KSFQOxy-A&s",
                    pricePerDay = 1.99,
                    startDate = now + 24 * 60 * 60 * 1000L,
                    endDate = now + 10 * 24 * 60 * 60 * 1000L
                )
            )
            saveCartItems(initialCart)
        }

        if (!prefs.contains("rentals"))
        {
            val now = System.currentTimeMillis()
            val day = 24 * 60 * 60 * 1000L
            val initialRentals = listOf(
                Rental(
                    rentalId = 1,
                    comicId = 1,
                    userId = 1,
                    comicTitle = "Solo Leveling",
                    comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAcXGr6r1HemXimBqcdQ2rIrAltN95YCLjwjQknzwnAA&s=10",
                    rentalDate = now - day,
                    dueDate = now + 6 * day,
                    status = RentalStatus.ACTIVE
                ),
                Rental(
                    rentalId = 2,
                    comicId = 2,
                    userId = 1,
                    comicTitle = "One Piece",
                    comicCoverUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-g1PV_IrIiWZMhewfUosHJhLFjBx79XbJ0KSFQOxy-A&s",
                    rentalDate = now - 10 * day,
                    dueDate = now - day,
                    status = RentalStatus.EXPIRED
                )
            )
            saveRentals(initialRentals)
        }
    }

    private inline fun <reified T> getObject(key: String, defaultValue: T): T
    {
        val stringValue = prefs.getString(key, null) ?: return defaultValue
        return try
        {
            json.decodeFromString<T>(stringValue)
        } catch (_: Exception)
        {
            defaultValue
        }
    }

    private inline fun <reified T> saveObject(key: String, value: T)
    {
        val stringValue = json.encodeToString(value)
        prefs.edit().putString(key, stringValue).apply()
    }


    // Cart manager
    fun getCartItems(): List<CartItem> = getObject("cart_items", emptyList())
    fun saveCartItems(items: List<CartItem>) = saveObject("cart_items", items)

    // Rentals manager
    fun getRentals(): List<Rental> = getObject("rentals", emptyList())
    fun saveRentals(rentals: List<Rental>) = saveObject("rentals", rentals)

    // User Profile manager
    fun getUserProfile(): User = getObject(
        "user_profile", User(
            id = "1",
            heroName = "YUIKA SAMA",
            realName = "Nam Thế Giới",
            email = "namthegioi65@gmail.com",
            rank = "HEROIC",
            phone = "+84 (09) 87 654 321",
            region = "Ha Noi",
            rentedCount = 128,
            activeCount = 14,
            avatarUrl = ""
        )
    )

    fun saveUserProfile(profile: User) = saveObject("user_profile", profile)


    // Wishlist manager
    fun getWishlist(): Set<Int> = getObject("wishlist", emptySet())
    fun saveWishlist(wishlist: Set<Int>) = saveObject("wishlist", wishlist)


    // Onboarding Manager
    fun isOnboardingCompleted(): Boolean = prefs.getBoolean("onboarding_completed", false)
    fun setOnboardingCompleted(completed: Boolean) =
        prefs.edit().putBoolean("onboarding_completed", completed).apply()


    // Favourite Genre manager
    fun getFavoriteGenres(): Set<String> = getObject("favorite_genres", emptySet())
    fun saveFavoriteGenres(genres: Set<String>) = saveObject("favorite_genres", genres)


    // Banned user manager
    fun getBannedUserEmails(): Set<String> = getObject("banned_users", AuthMockData.SEED_BANNED_EMAILS)
    fun saveBannedUserEmails(emails: Set<String>) = saveObject("banned_users", emails)


    // Admin Comics manager
    fun getAdminComics(): List<Comic> = getObject("admin_comics", emptyList())
    fun saveAdminComics(comics: List<Comic>) = saveObject("admin_comics", comics)

    // Bookmarks manager
    fun getBookmarks(): Map<Int, Int> = getObject("bookmarks", emptyMap())
    fun saveBookmarks(bookmarks: Map<Int, Int>) = saveObject("bookmarks", bookmarks)

    // Notification manager
    fun getNotificationsEnabled(): Boolean = prefs.getBoolean("notifications_enabled", true)
    fun setNotificationsEnabled(enabled: Boolean) =
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()

    fun getAppNotifications(): List<AppNotification> = getObject("app_notifications", emptyList())
    fun saveAppNotifications(notifications: List<AppNotification>) =
        saveObject("app_notifications", notifications)

    // Auth manager
    fun getUsersCredentials(): Map<String, String> {
        val stored = getObject<Map<String, String>?>("users_credentials", null)
        return if (stored == null) {
            AuthMockData.SEED_USER_CREDENTIALS
        } else {
            AuthMockData.SEED_USER_CREDENTIALS + stored
        }
    }

    fun saveUsersCredentials(users: Map<String, String>) = saveObject("users_credentials", users)

    // Login
    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
    fun setLoggedIn(loggedIn: Boolean) = prefs.edit().putBoolean("is_logged_in", loggedIn).apply()

    fun logOut()
    {
        setLoggedIn(false)
//        prefs.edit().remove("user_profile").apply()
    }

    fun saveUserAvatar(newAvatarUrl: String)
    {
        val currentProfile = getUserProfile()

        val updatedProfile = currentProfile.copy(avatarUrl = newAvatarUrl)

        saveUserProfile(updatedProfile)
    }
}