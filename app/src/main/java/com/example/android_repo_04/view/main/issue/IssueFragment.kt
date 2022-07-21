package com.example.android_repo_04.view.main.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.repository.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.databinding.FragmentIssueBinding
import com.example.android_repo_04.utils.EventObserver
import com.example.android_repo_04.view.listener.RefreshListener
import com.example.android_repo_04.view.listener.ScrollListener
import com.example.android_repo_04.viewmodel.CustomViewModelFactory
import com.example.android_repo_04.viewmodel.MainViewModel

class IssueFragment: Fragment(), RefreshListener {
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
        setIssues(issues)
        if (issues.isEmpty()) {
            showEmptyText()
        } else {
            showList()
        }
    }

    private val issueRefreshEventObserver: (Unit) -> Unit = {
        getSelectedIssues(viewModel.selectedIssue.value!!, 1)
    }

    private val issueClickEventObserver: (Unit) -> Unit = {
        binding.spinnerIssueOption.performClick()
    }

    private val issueReloadClickEventObserver: (Unit) -> Unit = {
        getSelectedIssues(viewModel.selectedIssue.value!!, 1)
        showEmptyProgress()
    }

    private val selectedIssueObserver: (Int) -> Unit = { position ->
        if (viewModel.prevSelectedIssue != position) {
            showProgress()
            viewModel.prevSelectedIssue = position
            getSelectedIssues(position, 1)
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
        setOnScrollListener()
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

    private fun setOnScrollListener() {
        binding.recyclerIssueIssue.addOnScrollListener(ScrollListener(this) { nextPage ->
            getSelectedIssues(viewModel.prevSelectedIssue, nextPage)
        })
    }

    private fun observeData() {
        viewModel.issue.observe(viewLifecycleOwner, issueObserver)
        viewModel.issueRefreshEvent.observe(viewLifecycleOwner, EventObserver(issueRefreshEventObserver))
        viewModel.issueClickEvent.observe(viewLifecycleOwner, EventObserver(issueClickEventObserver))
        viewModel.issueReloadClickEvent.observe(viewLifecycleOwner, EventObserver(issueReloadClickEventObserver))
        viewModel.selectedIssue.observe(viewLifecycleOwner, selectedIssueObserver)
    }

    private fun getSelectedIssues(position: Int, page: Int) {
        when (position) {
            0 -> viewModel.requestIssues(getString(R.string.state_open), page)
            1 -> viewModel.requestIssues(getString(R.string.state_closed), page)
            2 -> viewModel.requestIssues(getString(R.string.state_all), page)
        }
    }

    private fun showList() {
        binding.apply {
            refreshIssueIssue.visibility = View.VISIBLE
            textIssueEmpty.visibility = View.INVISIBLE
            btnIssueEmpty.visibility = View.INVISIBLE
            progressIssueEmpty.visibility = View.INVISIBLE
        }
    }

    private fun showEmptyText() {
        binding.apply {
            refreshIssueIssue.visibility = View.INVISIBLE
            textIssueEmpty.visibility = View.VISIBLE
            btnIssueEmpty.visibility = View.VISIBLE
            progressIssueEmpty.visibility = View.INVISIBLE
        }
    }

    private fun setIssues(issues: List<Issue>) {
        issueAdapter.submitList(issues)
        binding.refreshIssueIssue.isRefreshing = false
        binding.progressIssueLoading.visibility = View.INVISIBLE
    }

    private fun showEmptyProgress() {
        binding.progressIssueEmpty.visibility = View.VISIBLE
        binding.btnIssueEmpty.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showProgress() {
        binding.progressIssueLoading.visibility = View.VISIBLE
    }
}