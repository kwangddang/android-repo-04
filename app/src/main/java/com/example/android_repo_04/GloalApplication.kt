package com.example.android_repo_04

import android.app.Application
import com.chibatching.kotpref.Kotpref

class GloalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }
}