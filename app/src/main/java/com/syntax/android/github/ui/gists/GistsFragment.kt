package com.syntax.android.github.ui.gists

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.syntax.android.github.R
import com.syntax.android.github.model.*
import com.syntax.android.github.viewmodel.GistsViewModel
import kotlinx.android.synthetic.main.fragment_gists.*


class GistsFragment : Fragment(), GistAdapter.GistAdapterListener {

  private lateinit var gistsViewModel: GistsViewModel

  private val adapter = GistAdapter(mutableListOf(), this)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_gists, container, false)

    gistsViewModel = ViewModelProviders.of(this).get(GistsViewModel::class.java)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    gistsRecyclerView.layoutManager = LinearLayoutManager(context)
    gistsRecyclerView.adapter = adapter

    val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
    itemTouchHelper.attachToRecyclerView(gistsRecyclerView)

    gistsViewModel.getGists().observe(this, Observer<Either<List<Gist>>> { either ->
      if (either?.status == Status.SUCCESS && either.data != null) {
        adapter.updateGists(either.data)
      } else {
        if (either?.error == ApiError.GISTS) {
          Toast.makeText(context, getString(R.string.error_retrieving_gists), Toast.LENGTH_SHORT).show()
        }
      }
    })

    fab.setOnClickListener {
      showGistDialog()
    }
  }

  override fun deleteGist(gist: Gist) {
    gistsViewModel.deleteGist(gist).observe(this, Observer<Either<EmptyResponse>> { either ->
      if (either?.status == Status.SUCCESS) {
        adapter.deleteGist(gist)
      } else {
        if (either?.error == ApiError.DELETE_GIST) {
          Toast.makeText(context, getString(R.string.error_deleting_gist), Toast.LENGTH_SHORT).show()
        }
      }
    })
  }

  internal fun sendGist(description: String, filename: String, content: String) {
    gistsViewModel.sendGist(description, filename, content).observe(this, Observer<Either<Gist>> { either ->
      if (either?.status == Status.SUCCESS && either.data != null) {
        adapter.addGist(either.data)
      } else {
        if (either?.error == ApiError.POST_GIST) {
          Toast.makeText(context, getString(R.string.error_posting_gist), Toast.LENGTH_SHORT).show()
        }
      }
    })
  }
}