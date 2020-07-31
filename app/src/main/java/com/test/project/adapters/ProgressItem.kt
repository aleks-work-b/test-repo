package com.test.project.adapters

import android.view.View
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.project.R

class ProgressItem() : AbstractItem<ProgressViewHolder>() {

  override var identifier = 111L
  override val layoutRes = R.layout.item_progress
  override val type = R.id.itemProgressRootView

  override fun getViewHolder(v: View): ProgressViewHolder {
    return ProgressViewHolder(v)
  }

}