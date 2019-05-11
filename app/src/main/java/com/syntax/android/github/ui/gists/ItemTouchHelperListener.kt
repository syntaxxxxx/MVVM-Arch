package com.syntax.android.github.ui.gists

import android.support.v7.widget.RecyclerView


interface ItemTouchHelperListener {
  fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int)
}