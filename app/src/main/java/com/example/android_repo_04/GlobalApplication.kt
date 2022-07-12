package com.example.android_repo_04

import android.app.Application
import com.chibatching.kotpref.Kotpref

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }
}