package com.paulo.githubnavigator.network

import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.SearchUser
import com.paulo.githubnavigator.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{username}")
    suspend fun getInfoUser(
        @Path("username") username: String
    ): User

    @GET("users/{username}/repos")
    suspend fun getInfoRepositorysByUser(@Path("username") username: String): List<Repository>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String,
        @Query("type") type: String = "user"
    ): SearchUser
}