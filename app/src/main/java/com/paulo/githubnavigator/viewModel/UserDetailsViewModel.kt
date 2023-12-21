package com.paulo.githubnavigator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _repositories = MutableStateFlow(listOf<Repository>())
    val repositories = _repositories.asStateFlow()

    fun getUserInfo(username: String){
        viewModelScope.launch {
            _user.value = userRepository.getInfoUser(username)
        }
    }

    fun getRepositoriesByUserName(username: String){
        viewModelScope.launch {
            _repositories.value = userRepository.getInfoRepositorysByUser(username)
        }
    }
}