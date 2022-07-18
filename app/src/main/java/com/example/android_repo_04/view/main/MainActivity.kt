package com.example.android_repo_04.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.databinding.ActivityMainBinding
import com.example.android_repo_04.view.main.issue.IssueFragment
import com.example.android_repo_04.view.main.notification.NotificationFragment
import com.example.android_repo_04.view.profile.ProfileActivity
import com.example.android_repo_04.view.search.SearchActivity
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
        viewModel.position.value = 0
    }

    private val btnNotificationClickListener: (View) -> Unit = {
        viewModel.position.value = 1
    }

    private val imgProfileClickListener: (View) -> Unit = {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(getString(R.string.user_info),viewModel.user.value)
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private val imgSearchClickListener: (View) -> Unit = {
        startActivity(Intent(this,SearchActivity::class.java))
    }

    /* onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initBinding()
        observeData()
        setOnClickListeners()
        initFragmentManager()
        getIssues()
        getNotifications()
        getUser()
    }

    private fun initViewModel() {
        viewModel = MainViewModel.getInstance(GitHubApiRepository.getGitInstance()!!)
    }

    private fun initBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeData() {
        viewModel.position.observe(this, positionObserver)
    }

    private fun setOnClickListeners(){
        binding.btnMainIssue.setOnClickListener(btnIssueClickListener)
        binding.btnMainNotification.setOnClickListener(btnNotificationClickListener)
        binding.imgMainProfile.setOnClickListener(imgProfileClickListener)
        binding.imgMainSearch.setOnClickListener(imgSearchClickListener)
    }

    private fun initFragmentManager() {
        if (fragmentManager.fragments.isEmpty()) {
            fragmentManager.commit {
                viewModel.position.value = 0
                add(R.id.layout_main_fragment_container, notificationFragment, getString(R.string.tag_notification_fragment))
                add(R.id.layout_main_fragment_container, issueFragment, getString(R.string.tag_issue_fragment))
            }
        }
    }

    private fun getIssues() {
        if (viewModel.issue.value == null)
            viewModel.requestIssues(getString(R.string.state_open))
    }

    private fun getNotifications() {
        if (viewModel.notifications.value == null)
            viewModel.requestNotifications()
    }

    private fun getUser() {
        viewModel.requestUser()
    }

    private fun selectIssue(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_selected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_unselected)
    }

    private fun showIssueFragment(){
        fragmentManager.commit {
            show(fragmentManager.findFragmentByTag(getString(R.string.tag_issue_fragment))!!)
            hide(fragmentManager.findFragmentByTag(getString(R.string.tag_notification_fragment))!!)
        }
    }

    private fun selectNotification(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_unselected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_selected)
    }

    private fun showNotificationFragment(){
        fragmentManager.commit {
            show(fragmentManager.findFragmentByTag(getString(R.string.tag_notification_fragment))!!)
            hide(fragmentManager.findFragmentByTag(getString(R.string.tag_issue_fragment))!!)
        }
    }
}