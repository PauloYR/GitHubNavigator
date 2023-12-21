package com.paulo.githubnavigator.screen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.paulo.githubnavigator.NavigationItem
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.viewModel.UsersViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Users(
    navController: NavHostController
) {
    val usersViewModel: UsersViewModel = koinViewModel()
//    usersViewModel.getUsers()

    val users = usersViewModel.users.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        val handler = remember { Handler(Looper.getMainLooper()) }
        OutlinedTextField(
            value = usersViewModel.searchUserText,
            onValueChange = {
                usersViewModel.searchUserText = it

                handler.removeCallbacksAndMessages(null)

                handler.postDelayed({
                    usersViewModel.searchUser(usersViewModel.searchUserText)
                }, 300)
            },
            label = { Text("Busque por usuários") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Usuários",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (users.value.isNotEmpty()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(
                    items = users.value,
                    itemContent = {
                        UserItem(user = it, navController)
                    })
            }
        } else {
            Text(
                text = "Não foi possivel buscar os usuários",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun UserItem(user: User, navController: NavHostController, modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("${NavigationItem.UserDetails.route}/${user.login}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl
                ?: "https://seeklogo.com/images/G/github-logo-2E3852456C-seeklogo.com.png",
            contentDescription = "Avatar do usuário",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = user.login ?: "UserDetauils do usuário", modifier)
    }
}