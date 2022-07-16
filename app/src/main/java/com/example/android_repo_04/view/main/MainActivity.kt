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
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.databinding.ActivityMainBinding
import com.example.android_repo_04.view.main.issue.IssueFragment
import com.example.android_repo_04.view.main.notification.NotificationFragment
import com.example.android_repo_04.view.profile.ProfileActivity
import com.example.android_repo_04.viewmodel.MainViewModel
import com.example.android_repo_04.viewmodel.MainViewModelFactory

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

    private val btnIssueClickListener: (View) -> Unit = {
        selectIssue()
        showIssueFragment()
    }

    private val btnNotificationClickListener: (View) -> Unit = {
        selectNotification()
        showNotificationFragment()
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
        setOnClickListeners()
        initFragmentManager()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory()
        )[MainViewModel::class.java]
    }

    private fun setOnClickListeners(){
        binding.btnMainIssue.setOnClickListener(btnIssueClickListener)
        binding.btnMainNotification.setOnClickListener(btnNotificationClickListener)
        binding.imgMainProfile.setOnClickListener(imgProfileClickListener)
    }

    private fun initFragmentManager() {
        fragmentManager.commit {
            if (viewModel.position == null) {
                viewModel.changePosition(0)
                add(R.id.layout_main_fragment_container, notificationFragment, "notification")
                add(R.id.layout_main_fragment_container, issueFragment, "issue")
            } else {
                when (viewModel.position) {
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
        }
    }

    private fun selectIssue(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_selected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_unselected)
        viewModel.changePosition(0)
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
        viewModel.changePosition(1)
    }

    private fun showNotificationFragment(){
        fragmentManager.commit {
            fragmentManager.findFragmentByTag("notification")?.let { show(fragmentManager.findFragmentByTag("notification")!!) }
            fragmentManager.findFragmentByTag("issue")?.let { hide(fragmentManager.findFragmentByTag("issue")!!) }
        }
    }
}