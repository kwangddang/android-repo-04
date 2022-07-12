package com.example.android_repo_04.api

import com.example.android_repo_04.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitFactory {

    fun create(): GitHubService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.LOGIN_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }).build())
            .build()
            .create(GitHubService::class.java)

}