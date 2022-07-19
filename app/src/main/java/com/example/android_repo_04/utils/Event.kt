package com.example.android_repo_04.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

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

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}

fun MutableLiveData<Event<Unit>>.emit() {
    value = Event(Unit)
}

fun<T> MutableLiveData<Event<T>>.emit(data: T) {
    value = Event(data)
}