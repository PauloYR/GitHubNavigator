package com.paulo.githubnavigator.network

sealed class Output<out Response> {
    data class Success<out T>(val data: T?) : Output<T>()
    data class Loading<out T>(val data: T? = null) : Output<T>()
    data class Error<out T>(val error: Throwable) : Output<T>()
    object NetworkError : Output<Nothing>()
}