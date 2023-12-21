package com.paulo.githubnavigator.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun UserDetails(navController: NavHostController, username: String) {
    Text(text = username)
}