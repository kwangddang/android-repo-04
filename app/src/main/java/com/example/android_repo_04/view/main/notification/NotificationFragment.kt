package com.example.android_repo_04.view.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.databinding.FragmentNotificationBinding
import com.example.android_repo_04.viewmodel.MainViewModel
import com.example.android_repo_04.viewmodel.MainViewModelFactory


class NotificationFragment: Fragment(), NotificationSwipeListener {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter()
    }

    private val notificationObserver: (MutableList<Notification>) -> Unit = {
        notificationAdapter.notifications = it
        notificationAdapter.notifyDataSetChanged()
    }

    private val readNotificationObserver: (Int) -> Unit = {
        if (it >= 0) {
            notificationAdapter.notifications.removeAt(it)
            notificationAdapter.notifyItemRemoved(it)
        } else {
            Toast.makeText(context, "Notification을 읽는 데 실패하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        observeData()
        getNotifications()
        setItemTouchHelper()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(
                GitHubApiRepository.getGitInstance()!!
            )
        )[MainViewModel::class.java]
    }

    private fun initAdapter() {
        binding.recyclerNotifications.adapter = notificationAdapter
    }

    private fun observeData() {
        viewModel.notifications.observe(viewLifecycleOwner, notificationObserver)
        viewModel.readNotification.observe(viewLifecycleOwner, readNotificationObserver)
    }

    private fun getNotifications() {
        viewModel.requestNotifications()
    }

    private fun setItemTouchHelper() {
        context?.let {
            val itemTouchHelper = ItemTouchHelper(NotificationSwipeCallback(it, this))
            itemTouchHelper.attachToRecyclerView(binding.recyclerNotifications)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * swipe callback 에서 ui 작업을 수행하기 위함
     */
    override fun swipe(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        viewModel.requestToReadNotification(position)
    }
}