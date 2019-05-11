package com.syntax.android.github.ui.gists

import android.support.v7.util.DiffUtil
import com.syntax.android.github.model.Gist


class GistDiffCallback(private val oldGists: List<Gist>,
                       private val newGists: List<Gist>) : DiffUtil.Callback() {

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldGists[oldItemPosition].id == newGists[newItemPosition].id
  }

  override fun getOldListSize() = oldGists.size


  override fun getNewListSize() = newGists.size


  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val oldGist = oldGists[oldItemPosition]
    val newGist = newGists[newItemPosition]

    return oldGist == newGist
  }
}
