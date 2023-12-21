package com.paulo.githubnavigator.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.paulo.githubnavigator.NavigationItem
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.viewModel.UsersViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Users(
          navController:NavHostController) {
    val usersViewModel: UsersViewModel= koinViewModel()
    usersViewModel.getUsers()

    val users = usersViewModel.users.collectAsState()

    Column(modifier = Modifier
        .padding(16.dp)) {
        Text(
            text = "Usuários",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                items = users.value,
                itemContent = {
                    UserItem(user = it, navController)
                })
        }
    }
}

@Composable
fun UserItem(user: User, navController: NavHostController, modifier: Modifier = Modifier){

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navController.navigate("${NavigationItem.UserDetails.route}/${user.login}")
        },
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = user.avatarUrl ?: "https://seeklogo.com/images/G/github-logo-2E3852456C-seeklogo.com.png",
            contentDescription = "Avatar do usuário",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = user.login ?: "UserDetauils do usuário", modifier )
    }
}