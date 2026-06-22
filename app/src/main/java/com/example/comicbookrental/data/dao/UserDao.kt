package com.example.comicbookrental.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.comicbookrental.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao
{
    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("update user set isLoggedIn = true where email = :email and password = :password")
    fun logIn(email: String, password: String): Int

    @Query("update user set isLoggedIn = false where email = :email and password = :password")
    fun logOut(email: String, password: String): Int

    @Query("select * from user where isLoggedIn == 1 limit 1")
    fun getLoggedInUser(): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update
    fun updateUser(user: User)
}