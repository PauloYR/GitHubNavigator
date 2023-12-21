package com.paulo.githubnavigator.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.paulo.githubnavigator.R
import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.viewModel.UserDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserDetails(navController: NavHostController, username: String) {
    val viewModel = koinViewModel<UserDetailsViewModel>()

    viewModel.getUserInfo(username)
    viewModel.getRepositoriesByUserName(username)

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        UserInfo(viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))
        Repositories(viewModel)
    }
}

@Composable
fun UserInfo(viewModel: UserDetailsViewModel) {
    val user = viewModel.user.collectAsState()

    Column {
        AsyncImage(
            model = user.value?.avatarUrl
                ?: "https://seeklogo.com/images/G/github-logo-2E3852456C-seeklogo.com.png",
            contentDescription = "Avatar do usuário",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .align(alignment = Alignment.CenterHorizontally)
        )

        Text(
            text = user.value?.name ?: "",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = user.value?.login ?: "")
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Icon(
                Icons.Filled.Person,
                contentDescription = ""
            )

            Text(
                text = user.value?.followers.toString(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Seguidores")
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = user.value?.following.toString(),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Seguindo")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Icon(
                painterResource(id = R.drawable.apartment),
                contentDescription = ""
            )

            Text(text = user.value?.company ?: "")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = ""
            )

            Text(text = user.value?.location ?: "")
        }
    }
}

@Composable
fun Repositories(viewModel: UserDetailsViewModel) {
    val repositories = viewModel.repositories.collectAsState()
    if (repositories.value.isNotEmpty()) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                items = repositories.value,
                itemContent = {
                    RepositoryItem(it)
                })
        }
    } else {
        Text(
            text = "Não foi possivel buscar os repositorios",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun RepositoryItem(repository: Repository) {
    Column {
        Text(text = repository.name)
        Text(
            text = repository.description ?: "",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row {
            Icon(
                painterResource(id = R.drawable.book),
                contentDescription = ""
            )
        }

        Row {
            Icon(painterResource(id = R.drawable.eye), contentDescription = "")
            Text(repository.watchersCount.toString())
        }
    }
}