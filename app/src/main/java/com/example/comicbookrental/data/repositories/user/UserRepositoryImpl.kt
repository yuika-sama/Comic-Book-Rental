package com.example.comicbookrental.data.repositories.user

import com.example.comicbookrental.data.dao.UserDao
import com.example.comicbookrental.data.entities.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository
{
    override suspend fun getUserEmail(email: String): User?
    {
        return dao.getUserByEmail(email)
    }

    override fun logIn(email: String, password: String): Int
    {
        return dao.logIn(email, password)
    }

    override fun logOut(email: String, password: String): Int
    {
        return dao.logOut(email, password)
    }

    override fun getLoggedIUnUser(): Flow<User?>
    {
        return dao.getLoggedInUser()
    }

    override suspend fun insertUser(user: User): Long
    {
        return dao.insertUser(user)
    }

    override fun updateUser(user: User)
    {
        dao.updateUser(user)
    }
}