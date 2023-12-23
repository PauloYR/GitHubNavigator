package com.paulo.githubnavigator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

suspend fun advanceTimeBy(delayMillis: Long) {
    withContext(Dispatchers.Main) {
        delay(delayMillis)
    }
}