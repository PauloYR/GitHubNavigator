package com.paulo.githubnavigator.repository

import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.SearchUser
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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

class UserRepositoryImpl(private val httpClient: HttpClient) : UserRepository {
    override suspend fun getUsers() = flow {
        emit(Output.Loading())
        try {
            val response = httpClient.get("users")
            emit(Output.Success(response.body<List<User>>()))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }

    override suspend fun getInfoUser(username: String) = flow {
        emit(Output.Loading())
        try {
            emit(Output.Success(httpClient.get("users/${username}").body<User>()))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }

    override suspend fun getInfoRepositorysByUser(username: String) = flow {
        emit(Output.Loading())
        try {
            emit(Output.Success(httpClient.get("users/${username}/repos").body<List<Repository>>()))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }

    override suspend fun searchUsers(username: String) = flow {
        emit(Output.Loading())
        try {
            emit(Output.Success(httpClient.get("search/users") {
                url {
                    parameters.append("q", username)
                    parameters.append("type", "user")
                }
            }.body<SearchUser>().items))
        } catch (e: Exception) {
            emit(Output.Error(e))
        }
    }
}