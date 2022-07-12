package com.example.android_repo_04.api

import com.example.android_repo_04.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    var loginService: GitHubLoginService? = null
    var apiService: GitHubApiService? = null

    fun createLoginService(): GitHubLoginService? {
        if(loginService == null){
            loginService = Retrofit.Builder()
                .baseUrl(BuildConfig.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }).build())
                .build()
                .create(GitHubLoginService::class.java)
        }
        return loginService
    }

    fun createApiService(): GitHubApiService?{
        if(apiService == null) {
            apiService = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }).build())
                .build()
                .create(GitHubApiService::class.java)
        }
        return apiService
    }
}