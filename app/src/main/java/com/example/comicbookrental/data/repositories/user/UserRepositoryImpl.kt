package com.example.comicbookrental.data.repositories.user

import com.example.comicbookrental.data.dao.UserDao
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
)
{

}