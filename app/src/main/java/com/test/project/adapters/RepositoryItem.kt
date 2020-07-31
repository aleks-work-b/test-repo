package com.test.project.adapters

import android.view.View
import com.mikepenz.fastadapter.items.AbstractItem
import com.test.project.R
import com.test.project.models.GithubRepository

class RepositoryItem(
  val data: Data
) : AbstractItem<RepositoryViewHolder>() {

  override var identifier = data.id
  override val layoutRes = R.layout.item_repository
  override val type = R.id.itemRepositoryRootView

  override fun getViewHolder(v: View): RepositoryViewHolder {
    return RepositoryViewHolder(v)
  }

  data class Data(
    val id: Long,
    val repository: GithubRepository
  )

}