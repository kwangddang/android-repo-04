package com.example.android_repo_04.view.main.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_repo_04.api.GitHubApiRepository
import com.example.android_repo_04.api.GitHubLoginRepository
import com.example.android_repo_04.data.db.UserToken
import com.example.android_repo_04.data.dto.issue.Issue
import com.example.android_repo_04.databinding.FragmentIssueBinding
import com.example.android_repo_04.viewmodel.IssueViewModel
import com.example.android_repo_04.viewmodel.IssueViewModelFactory
import com.example.android_repo_04.viewmodel.LoginViewModel
import com.example.android_repo_04.viewmodel.LoginViewModelFactory

class IssueFragment: Fragment() {
    private var _binding: FragmentIssueBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: IssueViewModel

    private val issueObserver: (Issue) -> Unit = {
        Log.d("Test", it.toString())
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
        observeData()
        getIssues()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,
            IssueViewModelFactory(GitHubApiRepository.getGitInstance()!!)
        )[IssueViewModel::class.java]
    }

    private fun observeData() {
        viewModel.issue.observe(viewLifecycleOwner, issueObserver)
    }

    private fun getIssues() {
        viewModel.getIssues("token ${UserToken.accessToken}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}