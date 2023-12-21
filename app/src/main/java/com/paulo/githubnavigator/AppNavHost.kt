package com.paulo.githubnavigator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.paulo.githubnavigator.screen.UserDetails
import com.paulo.githubnavigator.screen.Users

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Users.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Users.route) {
            Users(navController)
        }
        composable("${NavigationItem.UserDetails.route}/{username}") {
            val username = it.arguments?.getString("username")
            UserDetails(navController,username ?: "Não foi possivel recuperar o usuário")
        }
    }
}