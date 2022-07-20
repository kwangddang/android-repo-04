package com.example.android_repo_04.view.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubLoginRepository
import com.example.android_repo_04.api.RetrofitFactory
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.utils.Event
import com.example.android_repo_04.utils.EventObserver
import com.example.android_repo_04.view.main.MainActivity
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    private val tokenObserver: (String) -> Unit = { token ->
        if (token.length < 5 && token != "") {
            binding.apply {
                progressLoginLogin.visibility = View.INVISIBLE
                btnLoginLogin.visibility = View.VISIBLE
            }
        } else if (token != "") {
            RetrofitFactory.accessToken = token
            startMainActivity()
        }
    }

    private val clickEventObserver: (Unit) -> Unit = { event ->
        val loginUri = Uri.parse(BuildConfig.LOGIN_URL).buildUpon()
            .appendPath(getString(R.string.login_path_auth))
            .appendQueryParameter(BuildConfig.CLIENT_ID_PARAM, BuildConfig.CLIENT_ID)
            .appendQueryParameter(BuildConfig.SCOPE_PARAM, getString(R.string.login_query_scope))
            .build()
        startActivity(Intent(Intent.ACTION_VIEW, loginUri))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBinding()
        getUserCode()
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
        viewModel.token.observe(this, tokenObserver)
        viewModel.clickEvent.observe(this, EventObserver(clickEventObserver))
    }

    private fun getUserCode() {
        if (intent.data != null) {
            viewModel.requestToken(intent.data?.getQueryParameter(getString(R.string.code))!!)
            binding.apply {
                progressLoginLogin.visibility = View.VISIBLE
                btnLoginLogin.visibility = View.INVISIBLE
            }
        }
    }

    private fun startMainActivity(){
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }
}