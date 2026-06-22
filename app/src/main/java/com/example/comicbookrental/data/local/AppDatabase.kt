package com.example.comicbookrental.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.comicbookrental.data.dao.UserDao
import com.example.comicbookrental.data.entities.User

@Database(
    entities =
        [
            User::class,
        ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(){
    abstract val userDao: UserDao
}