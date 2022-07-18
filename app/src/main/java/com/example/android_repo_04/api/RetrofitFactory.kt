package com.example.android_repo_04.api

import com.example.android_repo_04.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private var loginService: GitHubLoginService? = null
    private var apiService: GitHubApiService? = null
    var accessToken: String = ""

    fun createLoginService(): GitHubLoginService? {
        if (loginService == null) {
            loginService = Retrofit.Builder()
                .baseUrl(BuildConfig.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }).build())
                .build()
                .create(GitHubLoginService::class.java)
        }
        return loginService
    }

    fun createApiService(): GitHubApiService? {
        if (apiService == null) {
            apiService = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build())
                .build()
                .create(GitHubApiService::class.java)
        }
        return apiService
    }

    class HeaderInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader(BuildConfig.AUTHORIZATION_HEADER, "token $accessToken")
                .addHeader(BuildConfig.ACCEPT_HEADER, BuildConfig.ACCEPT)
                .build()
            return chain.proceed(newRequest)
        }
    }
}