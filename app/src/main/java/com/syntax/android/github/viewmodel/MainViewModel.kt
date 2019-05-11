package com.syntax.android.github.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.net.Uri
import android.util.Log
import com.syntax.android.github.BuildConfig
import com.syntax.android.github.app.Injection
import com.syntax.android.github.model.AccessToken
import com.syntax.android.github.model.AuthenticationPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val authApi = Injection.provideAuthApi()

  fun isAuthenticated() = AuthenticationPrefs.isAuthenticated()

  fun getAccessToken(uri: Uri, callback: () -> Unit) {
      val accessCode = uri.getQueryParameter("code")

      authApi.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, accessCode)
          .enqueue(object : Callback<AccessToken> {
            override fun onResponse(call: Call<AccessToken>?, response: Response<AccessToken>?) {
              val accessToken = response?.body()?.accessToken
              val tokenType = response?.body()?.tokenType
              if (accessToken != null && tokenType != null) {
                AuthenticationPrefs.saveAuthToken(accessToken)
                AuthenticationPrefs.saveTokenType(tokenType)
                callback()
              }
            }
            override fun onFailure(call: Call<AccessToken>?, t: Throwable?) {
              Log.e("MainViewModel", "Error getting token")
            }
          })
  }

  fun logout() {
    AuthenticationPrefs.saveAuthToken("")
    AuthenticationPrefs.clearUsername()
  }
}