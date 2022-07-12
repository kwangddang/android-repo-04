package com.example.android_repo_04.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_repo_04.R
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    val btnLoginClickListener: (View) -> Unit = {

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