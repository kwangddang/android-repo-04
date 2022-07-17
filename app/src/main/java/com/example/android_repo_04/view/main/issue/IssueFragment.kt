package com.example.android_repo_04.view.main.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.R
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.databinding.FragmentIssueBinding
import com.example.android_repo_04.viewmodel.MainViewModel
import com.example.android_repo_04.viewmodel.MainViewModelFactory

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
        issueAdapter.issue = issue
        issueAdapter.notifyDataSetChanged()
    }

    private val spinnerItemSelectedListener = object: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            getSelectedIssues(position)
            spinnerAdapter.selectedPosition = position
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
        setSpinnerClickListener()
        setOnClickListener()
        observeData()
        getIssues(getString(R.string.state_open))
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[MainViewModel::class.java]
    }

    private fun initRecyclerAdapter() {
        binding.recyclerIssueIssue.adapter = issueAdapter
    }

    private fun initSpinnerAdapter() {
        binding.spinnerIssueOption.adapter = spinnerAdapter
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

    private fun getIssues(state: String) {
        viewModel.requestIssues(state)
    }

    private fun getSelectedIssues(position: Int) {
        if (spinnerAdapter.selectedPosition != position) {
            when (position) {
                0 -> getIssues(getString(R.string.state_open))
                1 -> getIssues(getString(R.string.state_closed))
                2 -> getIssues(getString(R.string.state_all))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}