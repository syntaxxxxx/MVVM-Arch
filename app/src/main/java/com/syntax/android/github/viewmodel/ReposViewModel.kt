package com.syntax.android.github.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.syntax.android.github.app.Injection

class ReposViewModel(application: Application) : AndroidViewModel(application) {
  private val repository = Injection.provideRepository()
  private val allRepos = repository.getRepos()

  fun getRepos() = allRepos
}