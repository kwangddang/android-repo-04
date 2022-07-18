package com.example.android_repo_04.view.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.databinding.ActivityProfileBinding
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.ProfileViewModel

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
            CustomViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[ProfileViewModel::class.java]
    }

    private fun requestUser() {
        viewModel.requestUser()
    }

    private fun requestUserStarred() {
        viewModel.requestUserStarred()
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