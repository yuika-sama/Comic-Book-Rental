package com.example.comicbookrental.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =
        [
            // TODO: declare all entities here
        ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(){
    // TODO: declare all DAO interfaces here
}