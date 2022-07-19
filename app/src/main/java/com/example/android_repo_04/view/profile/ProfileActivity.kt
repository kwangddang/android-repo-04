package com.example.android_repo_04.view.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.data.dto.profile.User
import com.example.android_repo_04.databinding.ActivityProfileBinding
import com.example.android_repo_04.utils.Event
import com.example.android_repo_04.utils.EventObserver
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var viewModel: ProfileViewModel

    private lateinit var user: User
    private var starCount: Int = 0

    private val clickEventObserver: (Unit) -> Unit = { event ->
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserInfo()
        initViewModel()
        initBinding()
        observeData()
    }

    private fun getUserInfo() {
        user = intent.getParcelableExtra(getString(R.string.user_info))!!
        starCount = intent.getIntExtra(getString(R.string.star_count), 0)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            CustomViewModelFactory(null)
        )[ProfileViewModel::class.java]

        viewModel.user.value = user
        viewModel.star.value = starCount
    }

    private fun initBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ProfileActivity
        }
    }

    private fun observeData() {
        viewModel.clickEvent.observe(this, EventObserver(clickEventObserver))
    }
}