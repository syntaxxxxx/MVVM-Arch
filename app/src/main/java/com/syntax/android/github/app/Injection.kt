package com.syntax.android.github.app


import com.syntax.android.github.BuildConfig
import com.syntax.android.github.model.AuthenticationPrefs
import com.syntax.android.github.repository.AuthApi
import com.syntax.android.github.repository.GitHubApi
import com.syntax.android.github.repository.RemoteRepository
import com.syntax.android.github.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Injection {

    fun provideRepository(): Repository = RemoteRepository

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(provideLoggingInterceptor())

        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Authorization", "token ${AuthenticationPrefs.getAuthToken()}").build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    fun provideGitHubApi(): GitHubApi = provideRetrofit().create(GitHubApi::class.java)

    private fun provideAuthRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
    }
    fun provideAuthApi(): AuthApi = provideAuthRetrofit().create(AuthApi::class.java)
}