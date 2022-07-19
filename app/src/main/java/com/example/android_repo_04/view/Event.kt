package com.example.android_repo_04.view

import androidx.lifecycle.MutableLiveData

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}
fun MutableLiveData<Event<Unit>>.emit() {
    value = Event(Unit)
}

fun<T> MutableLiveData<Event<T>>.emit(data: T) {
    value = Event(data)
}