package com.syntax.android.github.ui.gists

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class ItemTouchHelperCallback(private val listener: ItemTouchHelperListener) : ItemTouchHelper.Callback() {

  override fun isLongPressDragEnabled() = false

  override fun isItemViewSwipeEnabled() = true

  override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
    return makeMovementFlags(0, ItemTouchHelper.START or ItemTouchHelper.END)
  }

  override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
    return false
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    listener.onItemDismiss(viewHolder, viewHolder.adapterPosition)
  }
}