package com.example.android_repo_04

import android.app.Application

class GitHubApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: GitHubApplication
    }
}