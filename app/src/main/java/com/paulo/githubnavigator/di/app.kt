package com.paulo.githubnavigator.di

import com.paulo.githubnavigator.network.GithubService
import com.paulo.githubnavigator.network.RetrofitClient
import org.koin.dsl.module

val appModule = module {
    single { RetrofitClient("https://api.github.com") }
    single { get<RetrofitClient>().create(GithubService::class.java) }


}