package com.example.android_repo_04.view.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.R
import com.example.android_repo_04.repository.GitHubLoginRepository
import com.example.android_repo_04.api.RetrofitFactory
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.utils.EventObserver
import com.example.android_repo_04.view.main.MainActivity
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    private val tokenObserver: (String) -> Unit = { token ->
        if (token.isNotEmpty()) {
            startMainActivity(token)
        } else {
            showButton()
        }
    }

    private val clickEventObserver: (Unit) -> Unit = { event ->
        login()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBinding()
        getToken()
        observeData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            CustomViewModelFactory(GitHubLoginRepository.getGitInstance()!!)
        )[LoginViewModel::class.java]
    }

    private fun initBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeData() {
        viewModel.tokenEvent.observe(this, EventObserver(tokenObserver))
        viewModel.clickEvent.observe(this, EventObserver(clickEventObserver))
    }

    private fun getToken() {
        if (intent.data != null) {
            viewModel.requestToken(intent.data?.getQueryParameter(getString(R.string.code))!!)
            showProgress()
        }
    }

    private fun login() {
        val loginUri = Uri.parse(BuildConfig.LOGIN_URL).buildUpon()
            .appendPath(getString(R.string.login_path_auth))
            .appendQueryParameter(BuildConfig.CLIENT_ID_PARAM, BuildConfig.CLIENT_ID)
            .appendQueryParameter(BuildConfig.SCOPE_PARAM, getString(R.string.login_query_scope))
            .build()
        startActivity(Intent(Intent.ACTION_VIEW, loginUri))
    }

    private fun showButton() {
        binding.progressLoginLogin.visibility = View.INVISIBLE
        binding.btnLoginLogin.visibility = View.VISIBLE
    }

    private fun showProgress() {
        binding.progressLoginLogin.visibility = View.VISIBLE
        binding.btnLoginLogin.visibility = View.INVISIBLE
    }

    private fun startMainActivity(token: String){
        RetrofitFactory.accessToken = token
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }
}