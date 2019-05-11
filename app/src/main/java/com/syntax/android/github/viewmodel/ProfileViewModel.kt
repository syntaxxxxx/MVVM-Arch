package com.syntax.android.github.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.syntax.android.github.app.Injection
import com.syntax.android.github.model.Either
import com.syntax.android.github.model.User
import com.syntax.android.github.model.UserRequest

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
  private val repository = Injection.provideRepository()
  private val user = repository.getUser()

  fun getUser() = user

  fun updateUser(company: String): LiveData<Either<User>> {
    val request = UserRequest(company)
    return repository.updateUser(request)
  }
}