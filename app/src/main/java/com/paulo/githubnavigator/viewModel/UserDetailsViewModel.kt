package com.paulo.githubnavigator.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.githubnavigator.model.Repository
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _user = MutableStateFlow<Output<User>>(Output.Loading())
    val user = _user.asStateFlow()

    private val _repositories = MutableStateFlow<Output<List<Repository>>>(Output.Loading())
    val repositories = _repositories.asStateFlow()

    fun getUserInfo(username: String){
        viewModelScope.launch {
             userRepository.getInfoUser(username).collect{
                 _user.value = it
             }
        }
    }

    fun getRepositoriesByUserName(username: String){
        viewModelScope.launch {
           userRepository.getInfoRepositorysByUser(username).collect{
               _repositories.value = it
           }
        }
    }
}