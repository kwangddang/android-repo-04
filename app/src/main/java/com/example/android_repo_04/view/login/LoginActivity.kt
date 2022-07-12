package com.example.android_repo_04.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubRepository
import com.example.android_repo_04.api.RetrofitFactory
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.viewmodel.LoginViewModel
import com.example.android_repo_04.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    private val btnLoginClickListener: (View) -> Unit = {
        startActivity(Intent(Intent.ACTION_VIEW, "${BuildConfig.LOGIN_URL}${getString(R.string.login_client_id)}${BuildConfig.CLIENT_ID}".toUri()))
    }

    private val tokenObserver: (String) -> Unit = { token ->
        if (token != "") {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        setOnClickListeners()
        getUserCode()
        observeData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            LoginViewModelFactory(GitHubRepository.getGitInstance()!!)
        )[LoginViewModel::class.java]
    }

    private fun observeData() {
        viewModel.token.observe(this, tokenObserver)
    }

    private fun getUserCode() {
        if (intent.data != null)
            viewModel.requestToken(intent.data?.getQueryParameter(getString(R.string.code))!!)
    }

    private fun setOnClickListeners(){
        binding.btnLoginLogin.setOnClickListener (btnLoginClickListener)
    }
}