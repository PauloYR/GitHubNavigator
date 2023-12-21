package com.paulo.githubnavigator.repository

import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.GithubService

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getInfoUser(
        username: String
    ): User?

    suspend fun getInfoRepositorysByUser(username: String): List<Repository>

    suspend fun searchUsers(username: String): List<User>
}

class UserRepositoryImpl(private val githubService: GithubService) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return try {
           githubService.getUsers()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getInfoUser(username: String): User? {
        return try {
            githubService.getInfoUser(username)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getInfoRepositorysByUser(username: String): List<Repository> {
        return try {
            githubService.getInfoRepositorysByUser(username)
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchUsers(username: String): List<User> {
        return try {
            githubService.searchUser(username).items
        } catch (e: Exception) {
            emptyList()
        }
    }
}