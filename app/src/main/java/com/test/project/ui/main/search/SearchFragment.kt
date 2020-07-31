package com.test.project.ui.main.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import com.test.project.R
import com.test.project.adapters.ProgressItem
import com.test.project.adapters.RepositoryItem
import com.test.project.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_search.*

class SearchFragment : BaseFragment<SearchViewModel>() {

  override val viewModelClass = SearchViewModel::class.java
  override val layoutId = R.layout.fragment_main_search

  private var fragmentCommander: FragmentCommander? = null
  private val itemAdapter = ItemAdapter<RepositoryItem>()
  private val footerAdapter = ItemAdapter<ProgressItem>()
  private lateinit var scrollListener: EndlessRecyclerOnScrollListener

  override fun setupCommander(context: Context) {
    super.setupCommander(context)

    if (context is FragmentCommander) {
      fragmentCommander = context
    }
  }

  override fun listenLiveData() {
    super.listenLiveData()

    listen(viewModel.listItemsLiveData) { handleItems(it) }
    listen(viewModel.errorLoadingEventLiveData) { handleErrorLoading() }
  }

  override fun onViewReady() {
    setupAdapters()

    mainSearchActionHistoryButton.setOnClickListener { actionHistoryClick() }
    mainSearchEditText.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

      override fun afterTextChanged(s: Editable?) {
        search(s?.toString())
      }
    })
  }

  private fun search(query: String?) {
    addProgressItem()
    viewModel.search(query)
    scrollListener.resetPageCount()
  }

  private fun setupAdapters() {
    val fastAdapter = FastAdapter.with(listOf(itemAdapter, footerAdapter))

    scrollListener = object : EndlessRecyclerOnScrollListener(footerAdapter) {
      override fun onLoadMore(currentPage: Int) {
        if (currentPage == 0) {
          return
        }

        addProgressItem()
        viewModel.loadMore()
      }
    }

    mainSearchRecyclerView.addOnScrollListener(scrollListener)

    fastAdapter.onClickListener = { view, adapter, item, position ->
      if (item is RepositoryItem) {
        itemClick(item)
      }
      false
    }


    mainSearchRecyclerView.adapter = fastAdapter
  }

  private fun itemClick(item: RepositoryItem) {
    viewModel.saveRepository(item)
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.data.repository.htmlUrl)))
  }

  private fun addProgressItem() {
    if (footerAdapter.adapterItemCount == 0) {
      footerAdapter.add(ProgressItem())
    }
  }

  private fun actionHistoryClick() {
    fragmentCommander?.toHistoryFragment()
  }

  private fun handleErrorLoading() {
    footerAdapter.clear()
  }

  private fun handleItems(list: List<RepositoryItem>) {
    footerAdapter.clear()

    if (list.isEmpty()) {
      itemAdapter.clear()
      return
    }

    FastAdapterDiffUtil[itemAdapter] = list
  }

  interface FragmentCommander : BaseFragment.FragmentCommander {
    fun toHistoryFragment()
  }

  companion object {
    private const val TAG = "SearchFragment"

    fun newInstance(): BaseFragment<*> = SearchFragment()
  }

}