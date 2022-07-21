package com.example.android_repo_04.view.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android_repo_04.repository.GitHubApiRepository
import com.example.android_repo_04.data.dto.notification.Notification
import com.example.android_repo_04.databinding.FragmentNotificationBinding
import com.example.android_repo_04.utils.EventObserver
import com.example.android_repo_04.view.listener.RefreshListener
import com.example.android_repo_04.view.listener.ScrollListener
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.MainViewModel


class NotificationFragment: Fragment(), NotificationSwipeListener, RefreshListener {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val notificationAdapter: NotificationAdapter by lazy {
        NotificationAdapter(viewModel)
    }

    private val notificationObserver: (MutableList<Notification>) -> Unit = { notification ->
        setNotifications(notification)
        if (notification.isEmpty()) {
            showEmptyText()
        } else {
            showList()
        }
    }

    private val notificationRefreshEventObserver: (Unit) -> Unit = {
        viewModel.requestNotifications()
    }

    private val notificationReloadEventObserver: (Unit) -> Unit = {
        viewModel.requestNotifications()
        showEmptyProgress()
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
        initBinding()
        initAdapter()
        setOnScrollListener()
        observeData()
        setItemTouchHelper()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(),
            CustomViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[MainViewModel::class.java]
    }

    private fun initBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initAdapter() {
        binding.recyclerNotifications.adapter = notificationAdapter
    }

    private fun setOnScrollListener() {
        binding.recyclerNotifications.addOnScrollListener(ScrollListener(this) { nextPage ->
            viewModel.requestNotifications(nextPage)
        })
    }

    private fun observeData() {
        viewModel.notifications.observe(viewLifecycleOwner, notificationObserver)
        viewModel.notificationRefreshEvent.observe(viewLifecycleOwner, EventObserver(notificationRefreshEventObserver))
        viewModel.notificationReloadClickEvent.observe(viewLifecycleOwner, EventObserver(notificationReloadEventObserver))
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

    private fun setNotifications(notification: MutableList<Notification>) {
        notificationAdapter.submitList(notification)
        binding.refreshNotifications.isRefreshing = false
        binding.progressNotificationLoading.visibility = View.INVISIBLE
    }

    private fun showList() {
        binding.apply {
            refreshNotifications.visibility = View.VISIBLE
            textNotificationsEmpty.visibility = View.INVISIBLE
            btnNotificationsEmpty.visibility = View.INVISIBLE
            progressNotificationEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmptyText() {
        binding.apply {
            refreshNotifications.visibility = View.INVISIBLE
            textNotificationsEmpty.visibility = View.VISIBLE
            btnNotificationsEmpty.visibility = View.VISIBLE
            progressNotificationEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmptyProgress() {
        binding.progressNotificationEmpty.visibility = View.VISIBLE
        binding.btnNotificationsEmpty.visibility = View.INVISIBLE
    }

    /**
     * swipe callback 에서 ui 작업을 수행하기 위함
     */
    override fun swipe(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        viewModel.requestToReadNotification(position)
    }

    override fun showProgress() {
        binding.progressNotificationLoading.visibility = View.VISIBLE
    }
}