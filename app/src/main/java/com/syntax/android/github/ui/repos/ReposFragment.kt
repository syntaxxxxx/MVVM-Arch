package com.syntax.android.github.ui.repos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.syntax.android.github.R
import com.syntax.android.github.model.ApiError
import com.syntax.android.github.model.Either
import com.syntax.android.github.model.Repo
import com.syntax.android.github.model.Status
import com.syntax.android.github.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.fragment_repos.*


class ReposFragment : Fragment() {

  private lateinit var reposViewModel: ReposViewModel

  private val adapter = RepoAdapter(mutableListOf())

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_repos, container, false)

    reposViewModel = ViewModelProviders.of(this).get(ReposViewModel::class.java)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    reposRecyclerView.layoutManager = LinearLayoutManager(context)
    reposRecyclerView.adapter = adapter

    reposViewModel.getRepos().observe(this, Observer<Either<List<Repo>>> { either ->
      if (either?.status == Status.SUCCESS && either.data != null) {
        adapter.updateRepos(either.data)
      } else {
        if (either?.error == ApiError.REPOS) {
          Toast.makeText(context, getString(R.string.error_retrieving_repos), Toast.LENGTH_SHORT).show()
        }
      }
    })
  }
}