package com.paulo.githubnavigator.viewModel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.githubnavigator.model.User
import com.paulo.githubnavigator.network.Output
import com.paulo.githubnavigator.repository.UserRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(private val userRepository: UserRepository) : ViewModel(),
    DefaultLifecycleObserver {

    private val _users = MutableStateFlow<Output<List<User>>>(Output.Loading())
    val users = _users.asStateFlow()

    private val _searchUserText = MutableStateFlow("")
    val searchUserText = _searchUserText.asStateFlow()

    @OptIn(FlowPreview::class)
    fun initialize() {
        viewModelScope.launch {
            searchUserText.debounce(500).collectLatest { input ->
                searchUser(input)
            }
        }
    }
    fun updateSearch(search: String) {
        _searchUserText.update { search }
    }
    private fun getUsers() {
        viewModelScope.launch {
            userRepository.getUsers().collect {
                _users.value = it
            }
        }
    }

    private fun searchUser(username: String) {
        if (username.isNotEmpty()) {
            viewModelScope.launch {
                userRepository.searchUsers(username).collect {
                    _users.value = it
                }
            }
        } else {
            getUsers()
        }
    }
}