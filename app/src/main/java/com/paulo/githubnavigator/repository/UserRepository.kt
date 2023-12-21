package com.paulo.githubnavigator.repository

import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.GithubService

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getInfoUser(
       username: String
    ): User
    suspend fun getInfoRepositorysByUser(): List<Repository>
}

class UserRepositoryImpl(private val githubService: GithubService): UserRepository{
    override suspend fun getUsers(): List<User> {
        return githubService.getUsers()
    }

    override suspend fun getInfoUser(username: String): User {
        return  githubService.getInfoUser(username)
    }

    override suspend fun getInfoRepositorysByUser(): List<Repository> {
       return githubService.getInfoRepositorysByUser()
    }
}