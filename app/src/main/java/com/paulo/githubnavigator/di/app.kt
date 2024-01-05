package com.paulo.githubnavigator.di

import com.paulo.githubnavigator.network.createHttpClient
import com.paulo.githubnavigator.repository.UserRepository
import com.paulo.githubnavigator.repository.UserRepositoryImpl
import com.paulo.githubnavigator.viewModel.UserDetailsViewModel
import com.paulo.githubnavigator.viewModel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient("https://api.github.com") }
    single<UserRepository> { UserRepositoryImpl(get()) }

    viewModel { UsersViewModel(get()) }
    viewModel { UserDetailsViewModel(get()) }
}