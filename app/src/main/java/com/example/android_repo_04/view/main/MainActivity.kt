package com.example.android_repo_04.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.android_repo_04.R
import com.example.android_repo_04.databinding.ActivityMainBinding
import com.example.android_repo_04.view.main.issue.IssueFragment
import com.example.android_repo_04.view.main.notification.NotificationFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val issueFragment : IssueFragment by lazy {
        IssueFragment()
    }

    private val notificationFragment : NotificationFragment by lazy {
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        initFragmentManager()
    }

    private fun setOnClickListeners(){
        binding.btnMainIssue.setOnClickListener(btnIssueClickListener)
        binding.btnMainNotification.setOnClickListener(btnNotificationClickListener)
    }

    private fun initFragmentManager() {
        fragmentManager.beginTransaction().apply {
            add(R.id.layout_main_fragment_container, notificationFragment)
            add(R.id.layout_main_fragment_container, issueFragment)
        }.commit()
    }

    private fun selectIssue(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_selected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_unselected)
    }

    private fun showIssueFragment(){
        fragmentManager.beginTransaction().hide(notificationFragment).commit()
        fragmentManager.beginTransaction().show(issueFragment).commit()
    }

    private fun selectNotification(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_unselected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_selected)
    }

    private fun showNotificationFragment(){
        fragmentManager.beginTransaction().hide(issueFragment).commit()
        fragmentManager.beginTransaction().show(notificationFragment).commit()
    }
}