package com.example.android_repo_04.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.R
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.viewmodel.LoginViewModel
import com.example.android_repo_04.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    private val btnLoginClickListener: (View) -> Unit = {
        startActivity(Intent(Intent.ACTION_VIEW, "${BuildConfig.LOGIN_URL}${getString(R.string.login_client_id)}${BuildConfig.CLIENT_ID}".toUri()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()

        viewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        checkCode()

        viewModel.token.observe(this) { token ->
            if (!token.equals("")) {
                Log.d("Test",token)
            }
        }
    }

    private fun checkCode() {
        if (intent.data != null) {
            viewModel.requestToken(intent.data?.getQueryParameter("code")!!)
        }
    }

    private fun setOnClickListeners(){
        binding.btnLoginLogin.setOnClickListener (btnLoginClickListener)
    }
}