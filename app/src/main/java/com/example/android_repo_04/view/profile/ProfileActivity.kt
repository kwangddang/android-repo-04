package com.example.android_repo_04.view.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.databinding.ActivityProfileBinding
import com.example.android_repo_04.viewmodel.ProfileViewModel
import com.example.android_repo_04.viewmodel.ProfileViewModelFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBinding()
        requestUser()
        requestUserStarred()
        setOnClickListeners()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            ProfileViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[ProfileViewModel::class.java]
    }

    private fun requestUser() {
        viewModel.requestUser("token ${UserToken.accessToken}")
    }

    private fun requestUserStarred() {
        viewModel.requestUserStarred("token ${UserToken.accessToken}")
    }

    private fun initBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProfileActivity
        }
    }

    private fun setOnClickListeners() {
        binding.imgProfileBack.setOnClickListener { onBackPressed() }
    }
}