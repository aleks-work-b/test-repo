package com.test.project.adapters

import android.view.View
import com.mikepenz.fastadapter.FastAdapter

class ProgressViewHolder(rootView: View) : FastAdapter.ViewHolder<ProgressItem>(rootView) {

  override fun bindView(item: ProgressItem, payloads: List<Any>) {}

  override fun unbindView(item: ProgressItem) {}

}