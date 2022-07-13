package com.example.android_repo_04.api

class GitHubApiRepository {
    suspend fun getIssues(token: String, state: String = "open", filter: String = "all"){

    }

    companion object{
        private var instance: GitHubApiRepository? = null
        fun getGitInstance(): GitHubApiRepository? {
            if(instance == null){
                instance = GitHubApiRepository()
            }
            return instance
        }
    }
}