package com.example.comicbookrental.data.repositories.admin

import com.example.comicbookrental.data.entities.AdminUser
import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.domain.repository.AdminUserRepository
import com.example.comicbookrental.utils.StoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminUserRepositoryImpl @Inject constructor(
    private val storeManager: StoreManager,
) : AdminUserRepository
{
    private val usersFlow = MutableStateFlow(buildUsers())

    override fun getUsers(): Flow<List<AdminUser>> = usersFlow

    override suspend fun banUser(email: String) {
        storeManager.saveBannedUserEmails(storeManager.getBannedUserEmails() + email)
        usersFlow.value = buildUsers()
    }

    override suspend fun unbanUser(email: String) {
        storeManager.saveBannedUserEmails(storeManager.getBannedUserEmails() - email)
        usersFlow.value = buildUsers()
    }

    override suspend fun refresh() {
        usersFlow.value = buildUsers()
    }

    private fun buildUsers(): List<AdminUser> {
        val banned = storeManager.getBannedUserEmails()
        return storeManager.getUsersCredentials().keys
            .map { email ->
                AdminUser(
                    email = email,
                    isBanned = email in banned,
                    isAdmin = email in AuthMockData.ADMIN_EMAILS,
                )
            }
            .sortedWith(compareBy({ !it.isAdmin }, { it.email }))
    }
}
