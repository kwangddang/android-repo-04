package com.example.android_repo_04.view.main.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.databinding.FragmentIssueBinding
import com.example.android_repo_04.view.Event
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.MainViewModel

class IssueFragment: Fragment() {
    private var _binding: FragmentIssueBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private val issueAdapter: IssueAdapter by lazy {
        IssueAdapter()
    }

    private val spinnerAdapter: SpinnerAdapter by lazy {
        SpinnerAdapter(requireContext())
    }

    private val issueObserver: (List<Issue>) -> Unit = { issues ->
        issueAdapter.replaceItem(issues)
        binding.refreshIssueIssue.isRefreshing = false
    }

    private val issueRefreshEventObserver: (Event<Unit>) -> Unit = { event ->
        if(event.getContentIfNotHandled() != null)
            getSelectedIssues(viewModel.selectedIssue.value!!)
    }

    private val issueClickEventObserver: (Event<Unit>) -> Unit = { event ->
        if(event.getContentIfNotHandled() != null)
            binding.spinnerIssueOption.performClick()
    }

    private val selectedIssueObserver: (Int) -> Unit = { position ->
        if(viewModel.prevSelectedIssue != position) {
            viewModel.prevSelectedIssue = position
            getSelectedIssues(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIssueBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initSpinnerAdapter()
        initBinding()
        initRecyclerAdapter()
        observeData()
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

    private fun initSpinnerAdapter() {
        binding.spinnerIssueOption.adapter = spinnerAdapter
    }

    private fun initRecyclerAdapter() {
        binding.recyclerIssueIssue.adapter = issueAdapter
    }


    private fun observeData() {
        viewModel.issue.observe(viewLifecycleOwner, issueObserver)
        viewModel.issueRefreshEvent.observe(viewLifecycleOwner, issueRefreshEventObserver)
        viewModel.issueClickEvent.observe(viewLifecycleOwner, issueClickEventObserver)
        viewModel.selectedIssue.observe(viewLifecycleOwner, selectedIssueObserver)
    }

    private fun getSelectedIssues(position: Int) {
        when (position) {
            0 -> viewModel.requestIssues(getString(R.string.state_open))
            1 -> viewModel.requestIssues(getString(R.string.state_closed))
            2 -> viewModel.requestIssues(getString(R.string.state_all))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}