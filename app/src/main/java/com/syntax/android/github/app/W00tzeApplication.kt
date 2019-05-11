package com.syntax.android.github.app

import android.app.Application
import android.content.Context

class W00tzeApplication : Application() {

  companion object {
    private lateinit var instance: W00tzeApplication

    fun getAppContext(): Context = instance.applicationContext
  }

  override fun onCreate() {
    instance = this
    super.onCreate()
  }
}