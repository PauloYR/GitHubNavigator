package com.paulo.githubnavigator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Long,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    val owner: Owner?,
    val description: String?,
    val size: Long,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
)

