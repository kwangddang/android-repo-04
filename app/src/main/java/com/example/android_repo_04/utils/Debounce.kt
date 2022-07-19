package com.example.android_repo_04.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private var job: Job? = null

fun debounce(
    time: Long,
    scope: CoroutineScope,
    callback: () -> Unit
) {
    job?.cancel()
    job = scope.launch {
        delay(time)
        callback()
    }
}