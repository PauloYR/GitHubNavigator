package com.paulo.githubnavigator.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchUser (
    val items: List<User>
)