package com.example.android_repo_04.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.R
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val btnLoginClickListener: (View) -> Unit = {
        startActivity(Intent(Intent.ACTION_VIEW, "${BuildConfig.LOGIN_URL}${BuildConfig.CLIENT_ID}".toUri()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.btnLoginLogin.setOnClickListener (btnLoginClickListener)
    }
}