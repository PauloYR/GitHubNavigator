package com.paulo.githubnavigator.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow(listOf<User>())
    val users = _users.asStateFlow()

    var searchUserText by mutableStateOf("")

    fun getUsers() {
        viewModelScope.launch {
            _users.value = userRepository.getUsers()
        }
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
            _users.value = if (username.isNotEmpty()) {
                userRepository.searchUsers(username)
            } else {
                userRepository.getUsers()
            }
        }
    }
}