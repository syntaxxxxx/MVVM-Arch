package com.syntax.android.github.ui.repos

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.syntax.android.github.R
import com.syntax.android.github.app.inflate
import com.syntax.android.github.model.Repo
import kotlinx.android.synthetic.main.list_item_repo.view.*


class RepoAdapter(private val repos: MutableList<Repo>)
  : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_repo))
  }

  override fun getItemCount() = repos.size

  fun updateRepos(repos: List<Repo>) {
    this.repos.clear()
    this.repos.addAll(repos)
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(repos[position])
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var repo: Repo

    fun bind(repo: Repo) {
      this.repo = repo
      itemView.repoName.text = repo.name
    }
  }
}