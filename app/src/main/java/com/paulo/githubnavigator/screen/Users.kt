package com.paulo.githubnavigator.screen

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.paulo.githubnavigator.NavigationItem
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import com.paulo.githubnavigator.viewModel.UsersViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Users(
    navController: NavHostController
) {
    val usersViewModel: UsersViewModel = koinViewModel()


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val search = usersViewModel.searchUserText.collectAsState()
        OutlinedTextField(
            value = search.value,
            onValueChange = {
                usersViewModel.updateSearch(it)
            },
            label = { Text("Busque por usuários") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Usuários",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListUsers(usersViewModel, navController)

        var functionExecuted by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(functionExecuted) {
            if (!functionExecuted) {
                functionExecuted = true
                usersViewModel.initialize()
            }
        }
    }
}

@Composable
fun ListUsers(usersViewModel: UsersViewModel, navController: NavHostController) {
    val users = usersViewModel.users.collectAsState()
    Column ( modifier = Modifier
        .fillMaxSize()){
        when (val output = users.value) {
            is Output.Success -> {
                val data = output.data ?: listOf()
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(
                        items = data,
                        itemContent = {
                            UserItem(user = it, navController)
                        })
                }
            }

            is Output.Error -> {
                Text(
                    text = "Não foi possivel buscar os usuários. Mais detalhes: ${output.error.message}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            else -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun UserItem(user: User, navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .testTag("rowUserDetails_${user.login}")
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