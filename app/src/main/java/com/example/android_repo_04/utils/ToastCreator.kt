package com.example.android_repo_04.utils

import android.content.Context
import android.widget.Toast
import com.example.android_repo_04.GitHubApplication
import com.example.android_repo_04.R

fun createToast(msg: String) {
    Toast.makeText(GitHubApplication.instance, msg, Toast.LENGTH_LONG).show()
}

fun createTokenErrorToast() {
    Toast.makeText(
        GitHubApplication.instance,
        GitHubApplication.instance.getString(R.string.toast_error_token),
        Toast.LENGTH_LONG
    ).show()
}

fun createErrorToast(errorCode: Int) {
    if (errorCode < 500)
        when (errorCode) {
            400 -> Toast.makeText(
                GitHubApplication.instance,
                GitHubApplication.instance.getString(R.string.toast_error_400),
                Toast.LENGTH_LONG
            ).show()
            403 -> Toast.makeText(
                GitHubApplication.instance,
                GitHubApplication.instance.getString(R.string.toast_error_403),
                Toast.LENGTH_LONG
            ).show()
            422 -> Toast.makeText(
                GitHubApplication.instance,
                GitHubApplication.instance.getString(R.string.toast_error_422),
                Toast.LENGTH_LONG
            ).show()
            else -> Toast.makeText(
                GitHubApplication.instance,
                "$errorCode ${GitHubApplication.instance.getString(R.string.toast_error)}",
                Toast.LENGTH_LONG
            ).show()
        }
    else Toast.makeText(
        GitHubApplication.instance,
        GitHubApplication.instance.getString(R.string.toast_error_500),
        Toast.LENGTH_LONG
    ).show()
}