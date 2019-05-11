package com.syntax.android.github.ui.gists

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.syntax.android.github.R
import com.syntax.android.github.app.inflate
import com.syntax.android.github.model.Gist
import kotlinx.android.synthetic.main.list_item_gist.view.*
import java.text.SimpleDateFormat
import java.util.*


class GistAdapter(private val gists: MutableList<Gist>, private val listener: GistAdapterListener)
  : RecyclerView.Adapter<GistAdapter.ViewHolder>(), ItemTouchHelperListener {

  companion object {
    private val DATE_FORMATTER = SimpleDateFormat("EEE M/dd/yyyy hh:mm a", Locale.US)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_gist))
  }

  override fun getItemCount() = gists.size

  fun updateGists(gists: List<Gist>) {
    this.gists.clear()
    this.gists.addAll(gists)
    notifyDataSetChanged()
  }

  fun addGist(gist: Gist) {
    this.gists.add(0, gist)
    notifyItemInserted(0)
  }

  fun deleteGist(gist: Gist) {
    val updatedGists = mutableListOf<Gist>()
    updatedGists.addAll(gists)
    updatedGists.remove(gist)

    val diffCallback = GistDiffCallback(this.gists, updatedGists)
    val diffResult = DiffUtil.calculateDiff(diffCallback)

    this.gists.clear()
    this.gists.addAll(updatedGists)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(gists[position])
  }

  override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
    listener.deleteGist(gists[position])
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var gist: Gist

    fun bind(gist: Gist) {
      this.gist = gist
      itemView.gistDescription.text = gist.description
      itemView.gistCreatedAt.text = DATE_FORMATTER.format(gist.createdAt)
      itemView.numFiles.text = gist.files.size.toString()
    }
  }

  interface GistAdapterListener {
    fun deleteGist(gist: Gist)
  }
}