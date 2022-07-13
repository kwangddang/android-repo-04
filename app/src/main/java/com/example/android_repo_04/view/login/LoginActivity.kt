package com.example.android_repo_04.view.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android_repo_04.BuildConfig
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubRepository
import com.example.android_repo_04.api.RetrofitFactory
import com.example.android_repo_04.data.UserToken
import com.example.android_repo_04.databinding.ActivityLoginBinding
import com.example.android_repo_04.view.main.MainActivity
import com.example.android_repo_04.viewmodel.LoginViewModel
import com.example.android_repo_04.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    private val btnLoginClickListener: (View) -> Unit = {
        val loginUri = Uri.parse(BuildConfig.LOGIN_URL).buildUpon() // Login URL을 가진 Uri.Builder Nested 객체 생성
            .appendPath(getString(R.string.login_path_auth))
            .appendQueryParameter("client_id", BuildConfig.CLIENT_ID)
            .appendQueryParameter("scope", getString(R.string.login_query_scope))
            .build()
        startActivity(Intent(Intent.ACTION_VIEW, loginUri))
    }

    private val tokenObserver: (String) -> Unit = { token ->
        if (token == "error") {
            Toast.makeText(this, "앱의 상태 정보가 정확하지 않습니다. 개발자에게 문의 바랍니다.", Toast.LENGTH_SHORT).show()
            binding.apply {
                progressLoginLogin.visibility = View.INVISIBLE
                btnLoginLogin.visibility = View.VISIBLE
            }
        } else if (token != "") {
            UserToken.accessToken = token
            startMainActivity()
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
        if (intent.data != null) {
            viewModel.requestToken(intent.data?.getQueryParameter(getString(R.string.code))!!)
            binding.apply {
                progressLoginLogin.visibility = View.VISIBLE
                btnLoginLogin.visibility = View.INVISIBLE
            }
        }
    }

    private fun setOnClickListeners(){
        binding.btnLoginLogin.setOnClickListener (btnLoginClickListener)
    }

    private fun startMainActivity(){
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }
}