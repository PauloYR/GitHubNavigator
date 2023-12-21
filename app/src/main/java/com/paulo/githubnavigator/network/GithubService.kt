package com.paulo.githubnavigator.network

import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{username}")
    suspend fun getInfoUser(
        @Path("username") username: String
    ): User

    @GET("users/{username}/repos")
    suspend fun getInfoRepositorysByUser(): List<Repository>
}