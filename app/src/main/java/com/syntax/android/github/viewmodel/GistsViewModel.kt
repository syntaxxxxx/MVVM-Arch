
package com.syntax.android.github.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.syntax.android.github.app.Injection
import com.syntax.android.github.model.*


class GistsViewModel(application: Application) : AndroidViewModel(application) {
  private val repository = Injection.provideRepository()
  private val allGists = repository.getGists()

  fun getGists() = allGists

  fun sendGist(description: String, filename: String, content: String): LiveData<Either<Gist>> {
    val gistFile = GistFile(content)
    val gistFiles = mapOf(filename to gistFile)
    val request = GistRequest(description, gistFiles)

    return repository.postGist(request)
  }

  fun deleteGist(gist: Gist)= repository.deleteGist(gist.id)
}