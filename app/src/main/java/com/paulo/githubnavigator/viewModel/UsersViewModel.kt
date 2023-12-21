package com.paulo.githubnavigator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _users = MutableStateFlow(listOf<User>())
    val users = _users.asStateFlow()

    fun getUsers(){
        viewModelScope.launch{
            _users.value = userRepository.getUsers()
        }
    }
}