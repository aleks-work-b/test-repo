package com.test.project.ui.main.history

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.test.project.R
import com.test.project.adapters.RepositoryItem
import com.test.project.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_history.*

class HistoryFragment : BaseFragment<HistoryViewModel>() {

  override val viewModelClass = HistoryViewModel::class.java
  override val layoutId = R.layout.fragment_main_history

  private var fragmentCommander: FragmentCommander? = null
  private val itemAdapter = ItemAdapter<RepositoryItem>()

  override fun setupCommander(context: Context) {
    super.setupCommander(context)

    if (context is FragmentCommander) {
      fragmentCommander = context
    }
  }

  override fun listenLiveData() {
    super.listenLiveData()

    listen(viewModel.listItemsLiveData) { handleItems(it) }
  }

  override fun onViewReady() {
    setupAdapters()
    mainHistoryActionBackButton.setOnClickListener { actionBackClick() }

    viewModel.requestItems()
  }

  private fun setupAdapters() {
    val fastAdapter = FastAdapter.with(itemAdapter)

    fastAdapter.onClickListener = { _, _, item, _ ->
      itemClick(item)
      false
    }

    mainHistoryRecyclerView.adapter = fastAdapter
  }

  private fun itemClick(item: RepositoryItem) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.data.repository.htmlUrl)))
  }

  private fun handleItems(list: List<RepositoryItem>) {
    FastAdapterDiffUtil[itemAdapter] = list
  }

  private fun actionBackClick() {
    fragmentCommander?.backClick()
  }

  interface FragmentCommander : BaseFragment.FragmentCommander {
    fun backClick()
  }

  companion object {
    const val TAG = "HistoryFragment"

    fun newInstance(): BaseFragment<*> = HistoryFragment()
  }

}