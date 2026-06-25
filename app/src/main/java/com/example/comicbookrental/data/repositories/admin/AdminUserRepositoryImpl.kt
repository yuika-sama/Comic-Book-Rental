package com.example.comicbookrental.data.repositories.admin

import com.example.comicbookrental.data.entities.AdminUser
import com.example.comicbookrental.data.mock.AuthMockData
import com.example.comicbookrental.domain.repository.AdminUserRepository
import com.example.comicbookrental.services.StorageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminUserRepositoryImpl @Inject constructor(
    private val storageManager: StorageManager,
) : AdminUserRepository
{
    private val usersFlow = MutableStateFlow(buildUsers())

    override fun getUsers(): Flow<List<AdminUser>> = usersFlow

    override suspend fun banUser(email: String) {
        storageManager.saveBannedUserEmails(storageManager.getBannedUserEmails() + email)
        usersFlow.value = buildUsers()
    }

    override suspend fun unbanUser(email: String) {
        storageManager.saveBannedUserEmails(storageManager.getBannedUserEmails() - email)
        usersFlow.value = buildUsers()
    }

    override suspend fun refresh() {
        usersFlow.value = buildUsers()
    }

    private fun buildUsers(): List<AdminUser> {
        val banned = storageManager.getBannedUserEmails()
        return storageManager.getUsersCredentials().keys
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
