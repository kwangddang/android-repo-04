package com.example.android_repo_04.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.databinding.ActivityMainBinding
import com.example.android_repo_04.view.main.issue.IssueFragment
import com.example.android_repo_04.view.main.notification.NotificationFragment
import com.example.android_repo_04.view.profile.ProfileActivity
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    private lateinit var viewModel: MainViewModel

    private val issueFragment: IssueFragment by lazy {
        IssueFragment()
    }

    private val notificationFragment: NotificationFragment by lazy {
        NotificationFragment()
    }

    private val fragmentManager: FragmentManager by lazy {
        supportFragmentManager
    }

    private val positionObserver: (Int) -> Unit = {
        when (it) {
            0 -> {
                selectIssue()
                showIssueFragment()
            }
            1 -> {
                selectNotification()
                showNotificationFragment()
            }
        }
    }

    private val btnIssueClickListener: (View) -> Unit = {
        viewModel.changePosition(0)
    }

    private val btnNotificationClickListener: (View) -> Unit = {
        viewModel.changePosition(1)
    }

    private val imgProfileClickListener: (View) -> Unit = {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    /* onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        observeData()
        setOnClickListeners()
        initFragmentManager()
        getIssues()
        getNotifications()
    }

    private fun initViewModel() {
        viewModel = MainViewModel.getInstance(GitHubApiRepository.getGitInstance()!!)
    }

    private fun observeData() {
        viewModel.position.observe(this, positionObserver)
    }

    private fun setOnClickListeners(){
        binding.btnMainIssue.setOnClickListener(btnIssueClickListener)
        binding.btnMainNotification.setOnClickListener(btnNotificationClickListener)
        binding.imgMainProfile.setOnClickListener(imgProfileClickListener)
    }

    private fun initFragmentManager() {
        if (fragmentManager.fragments.isEmpty()) {
            fragmentManager.commit {
                viewModel.changePosition(0)
                add(R.id.layout_main_fragment_container, notificationFragment, "notification")
                add(R.id.layout_main_fragment_container, issueFragment, "issue")
            }
        }
    }

    private fun getIssues() {
        if (viewModel.issue.value == null)
            viewModel.requestIssues("token ${UserToken.accessToken}", IssueFragment.OPEN)
    }

    private fun getNotifications() {
        if (viewModel.notifications.value == null)
            viewModel.requestNotifications("token ${UserToken.accessToken}")
    }

    private fun selectIssue(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_selected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_unselected)
    }

    private fun showIssueFragment(){
        fragmentManager.commit {
            fragmentManager.findFragmentByTag("issue")?.let { show(fragmentManager.findFragmentByTag("issue")!!) }
            fragmentManager.findFragmentByTag("notification")?.let { hide(fragmentManager.findFragmentByTag("notification")!!) }
        }
    }

    private fun selectNotification(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_unselected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_selected)
    }

    private fun showNotificationFragment(){
        fragmentManager.commit {
            fragmentManager.findFragmentByTag("notification")?.let { show(fragmentManager.findFragmentByTag("notification")!!) }
            fragmentManager.findFragmentByTag("issue")?.let { hide(fragmentManager.findFragmentByTag("issue")!!) }
        }
    }
}