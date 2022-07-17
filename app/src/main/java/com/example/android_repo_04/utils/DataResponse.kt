package com.example.android_repo_04.utils

sealed class DataResponse<T> {
    data class Success<T>(val data: T? = null): DataResponse<T>()
    data class Error<T>(val errorCode: Int? = null): DataResponse<T>()
}

