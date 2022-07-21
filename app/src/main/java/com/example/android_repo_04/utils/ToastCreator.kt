package com.example.android_repo_04.utils

import android.widget.Toast
import com.example.android_repo_04.GitHubApplication
import com.example.android_repo_04.R

fun createTokenErrorToast() {
    Toast.makeText(
        GitHubApplication.instance,
        GitHubApplication.instance.getString(R.string.toast_error_token),
        Toast.LENGTH_LONG
    ).show()
}

fun createErrorToast(errorCode: Int) {
    val errorMessage = if (errorCode < 500) {
        when(errorCode) {
            400 -> R.string.toast_error_400
            403 -> R.string.toast_error_403
            422 -> R.string.toast_error_422
            else -> R.string.toast_error
        }
    } else {
        R.string.toast_error_500
    }
    GitHubApplication.instance.run {
        Toast.makeText(this, this.getString(errorMessage), Toast.LENGTH_LONG).show()
    }
}