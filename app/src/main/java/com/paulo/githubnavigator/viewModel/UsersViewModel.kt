package com.paulo.githubnavigator.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val userRepository: UserRepository) : ViewModel(),
    DefaultLifecycleObserver {

    private val _users = MutableStateFlow<Output<List<User>>>(Output.Loading())
    val users = _users.asStateFlow()

    var searchUserText by mutableStateOf("")

    private var userGetIt = false

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect {
                _users.value = it
            }
        }
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
            if (username.isNotEmpty()) {
                userRepository.searchUsers(username).collect {
                    _users.value = it
                }
            } else {
                userRepository.getUsers().collect {
                    _users.value = it
                }
            }
        }
    }
}