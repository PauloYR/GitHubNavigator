package com.paulo.githubnavigator.repository

import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.GithubService
import com.paulo.githubnavigator.network.Output
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    suspend fun getUsers(): Flow<Output<List<User>>>
    suspend fun getInfoUser(
        username: String
    ): Flow<Output<User>>

    suspend fun getInfoRepositorysByUser(username: String): Flow<Output<List<Repository>>>

    suspend fun searchUsers(username: String): Flow<Output<List<User>>>
}

class UserRepositoryImpl(private val githubService: GithubService) : UserRepository {
    override suspend fun getUsers() = flow {
        emit(Output.Loading())
        try {
            emit(Output.Success(githubService.getUsers()))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }

    override suspend fun getInfoUser(username: String) = flow {
        emit(Output.Loading())
         try {
            emit(Output.Success(githubService.getInfoUser(username)))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }

    override suspend fun getInfoRepositorysByUser(username: String) = flow {
        emit(Output.Loading())
         try {
            emit(Output.Success(githubService.getInfoRepositorysByUser(username)))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }

    override suspend fun searchUsers(username: String) = flow {
        emit(Output.Loading())
        try {
            emit(Output.Success(githubService.searchUser(username).items))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }
}