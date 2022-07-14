package com.example.android_repo_04.view.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.databinding.FragmentNotificationBinding
import com.example.android_repo_04.viewmodel.NotificationViewModel
import com.example.android_repo_04.viewmodel.NotificationViewModelFactory

class NotificationFragment: Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NotificationViewModel

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter()
    }

    private val notificationObserver: (List<Notification>) -> Unit = {
        notificationAdapter.notifications = it
        notificationAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        observeData()
        getNotifications()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            NotificationViewModelFactory(
                GitHubApiRepository.getGitInstance()!!
            )
        )[NotificationViewModel::class.java]
    }

    private fun initAdapter() {
        binding.recyclerNotifications.adapter = notificationAdapter
    }

    private fun observeData() {
        viewModel.notification.observe(viewLifecycleOwner, notificationObserver)
    }

    private fun getNotifications() {
        viewModel.requestNotifications("token ${UserToken.accessToken}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}