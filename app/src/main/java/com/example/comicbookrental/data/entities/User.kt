package com.example.comicbookrental.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import dagger.Provides

@Entity(tableName="user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val password: String,
    val userName: String,
    val isLoggedIn: Boolean = false,
    val avatarUrl: String? = null,
)