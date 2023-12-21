package com.paulo.githubnavigator

enum class Screen {
    USERS,
    USERS_DETAILS,
}
sealed class NavigationItem(val route: String) {
    object Users : NavigationItem(Screen.USERS.name)
    object UserDetails : NavigationItem(Screen.USERS_DETAILS.name)
}