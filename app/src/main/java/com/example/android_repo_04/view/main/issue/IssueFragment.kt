package com.example.android_repo_04.view.main.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.databinding.FragmentIssueBinding
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

    private val issueObserver: (Issue) -> Unit = { issue ->
        issueAdapter.replaceItem(issue)
        binding.refreshIssueIssue.isRefreshing = false
    }

    private val spinnerItemSelectedListener = object: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            spinnerAdapter.selectedPosition = position
            if (viewModel.selectedIssue != position) {
                viewModel.selectedIssue = position
                getSelectedIssues(position)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    private val spinnerWindowFocusChangeListener: (Boolean) -> Unit = {
        if(it) {
            binding.layoutIssueInnerContainer.setBackgroundResource(R.drawable.background_round_navy)
        } else {
            binding.layoutIssueInnerContainer.setBackgroundResource(R.drawable.background_round_navy_selected)
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
        initRecyclerAdapter()
        initSpinnerAdapter()
        setRefreshListener()
        setSpinnerClickListener()
        setOnClickListener()
        observeData()
    }

    private fun initViewModel() {
        viewModel = MainViewModel.getInstance(GitHubApiRepository.getGitInstance()!!)
    }

    private fun initRecyclerAdapter() {
        binding.recyclerIssueIssue.adapter = issueAdapter
    }

    private fun initSpinnerAdapter() {
        binding.spinnerIssueOption.adapter = spinnerAdapter
    }

    private fun setRefreshListener() {
        binding.refreshIssueIssue.setOnRefreshListener {
            getSelectedIssues(spinnerAdapter.selectedPosition)
        }
    }

    private fun setSpinnerClickListener() {
        binding.spinnerIssueOption.onItemSelectedListener = spinnerItemSelectedListener
        binding.spinnerIssueOption.viewTreeObserver.addOnWindowFocusChangeListener(spinnerWindowFocusChangeListener)
    }

    private fun setOnClickListener() {
        binding.layoutIssueInnerContainer.setOnClickListener {
            binding.spinnerIssueOption.performClick()
        }
    }

    private fun observeData() {
        viewModel.issue.observe(viewLifecycleOwner, issueObserver)
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