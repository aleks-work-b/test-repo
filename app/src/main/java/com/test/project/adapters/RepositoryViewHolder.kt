package com.test.project.adapters

import android.annotation.SuppressLint
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryViewHolder(rootView: View) : FastAdapter.ViewHolder<RepositoryItem>(rootView) {

  private val tvName = rootView.itemRepositoryNameTextView
  private val tvDescription = rootView.itemRepositoryDescriptionTextView
  private val tvStars = rootView.itemRepositoryStarsTextView
  private val tvUrl = rootView.itemRepositoryUrlTextView

  @SuppressLint("SetTextI18n")
  override fun bindView(item: RepositoryItem, payloads: List<Any>) {
    tvName.text = "Name: ${item.data.repository.name}"
    tvDescription.text = "Description: ${item.data.repository.description}"
    tvStars.text = "Stars: ${item.data.repository.stargazersCount}"
    tvUrl.text = item.data.repository.htmlUrl
  }

  override fun unbindView(item: RepositoryItem) {
    tvName.text = null
    tvDescription.text = null
    tvStars.text = null
    tvUrl.text = null
  }

}