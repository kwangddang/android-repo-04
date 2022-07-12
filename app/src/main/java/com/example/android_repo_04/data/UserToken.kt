package com.example.android_repo_04.data

import com.chibatching.kotpref.KotprefModel

object UserToken : KotprefModel() {
    var accessToken: String by stringPref()
}