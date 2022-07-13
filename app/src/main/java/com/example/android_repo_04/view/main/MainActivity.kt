package com.example.android_repo_04.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.android_repo_04.R
import com.example.android_repo_04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val btnIssueClickListener: (View) -> Unit = {
        selectIssue()
    }

    private val btnNotificationClickListener: (View) -> Unit = {
        selectNotification()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.btnMainIssue.setOnClickListener(btnIssueClickListener)
        binding.btnMainNotification.setOnClickListener(btnNotificationClickListener)
    }

    private fun selectIssue(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_selected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_unselected)
    }

    private fun selectNotification(){
        binding.btnMainIssue.setBackgroundResource(R.drawable.btn_round_unselected)
        binding.btnMainNotification.setBackgroundResource(R.drawable.btn_round_selected)
    }
}