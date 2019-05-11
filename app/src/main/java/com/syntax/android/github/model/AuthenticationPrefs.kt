package com.syntax.android.github.model

import android.preference.PreferenceManager
import com.syntax.android.github.app.W00tzeApplication


object AuthenticationPrefs {

  private const val KEY_AUTH_TOKEN = "KEY_AUTH_TOKEN"

  private const val KEY_TOKEN_TYPE = "KEY_TOKEN_TYPE"

  private const val KEY_USERNAME = "KEY_USERNAME"

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(W00tzeApplication.getAppContext())

  fun saveAuthToken(token: String) {
    val editor = sharedPrefs().edit()
    editor.putString(KEY_AUTH_TOKEN, token).apply()
  }

  fun getAuthToken(): String = sharedPrefs().getString(KEY_AUTH_TOKEN, "")

  fun isAuthenticated() = !getAuthToken().isBlank()

  fun saveTokenType(tokenType: String) {
    val editor = sharedPrefs().edit()
    editor.putString(KEY_TOKEN_TYPE, tokenType).apply()
  }

  fun getTokenType(): String = sharedPrefs().getString(KEY_TOKEN_TYPE, "")

  fun saveUsername(username: String) {
    val editor = sharedPrefs().edit()
    editor.putString(KEY_USERNAME, username).apply()
  }

  fun getUsername(): String = sharedPrefs().getString(KEY_USERNAME, "w00tze")

  fun clearUsername() = sharedPrefs().edit().remove(KEY_USERNAME).apply()
}