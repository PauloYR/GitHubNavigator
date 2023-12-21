package com.paulo.githubnavigator.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String?,
    val id: Int?,
    val nodeId: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    val gravatarId: String?,
    val url: String?,
    val htmlUrl: String?,
    val followersUrl: String?,
    val followingUrl: String?,
    val gistsUrl: String?,
    val starredUrl: String?,
    val subscriptionsUrl: String?,
    val organizationsUrl: String?,
    val reposUrl: String?,
    val eventsUrl: String?,
    val receivedEventsUrl: String?,
    val type: String?,
    val siteAdmin: Boolean?,
    val name: String?,
    val followers: Int?,
    val following: Int?,
    val location: String?,
    val company: String?,
    val bio: String?
)