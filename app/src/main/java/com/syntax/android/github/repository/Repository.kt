package com.syntax.android.github.repository

import android.arch.lifecycle.LiveData
import com.syntax.android.github.model.*

interface Repository {
  fun getRepos(): LiveData<Either<List<Repo>>>
  fun getGists(): LiveData<Either<List<Gist>>>
  fun getUser(): LiveData<Either<User>>
  fun postGist(request: GistRequest): LiveData<Either<Gist>>
  fun deleteGist(gistId: String): LiveData<Either<EmptyResponse>>
  fun updateUser(request: UserRequest): LiveData<Either<User>>
}

