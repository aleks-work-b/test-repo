package com.test.project.ui.main.search

import androidx.lifecycle.MutableLiveData
import com.test.project.adapters.RepositoryItem
import com.test.project.managers.RepositoryManager
import com.test.project.models.GithubRepository
import com.test.project.models.SearchResult
import com.test.project.network.service.GithubNetworkManager
import com.test.project.ui.common.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(
  private val githubNetworkManager: GithubNetworkManager,
  private val repositoryManager: RepositoryManager
) : BaseViewModel() {

  val listItemsLiveData = MutableLiveData<List<RepositoryItem>>(emptyList())
  val errorLoadingEventLiveData = MutableLiveData<Boolean>()

  private val firstScheduler = Schedulers.newThread()
  private val secondScheduler = Schedulers.newThread()

  private var loadMoreDisposable: Disposable? = null
  private var currentPage: Int = 1
  private var query: String = ""

  fun search(name: String?) {
    compositeDisposable.clear()
    currentPage = 1

    if (name.isNullOrEmpty()) {
      query = ""
      listItemsLiveData.value = emptyList()
      return
    }

    query = name

    compositeDisposable.add(
      loadRepositories()
        .subscribe({ handleRepositoriesSuccess(it) }, { handleRepositoriesFailure(it) })
    )
  }

  fun loadMore() {
    currentPage++
    loadMoreDisposable = loadRepositories()
      .subscribe(
        { handleLoadMoreRepositoriesSuccess(it) },
        { handleLoadMoreRepositoriesFailure(it) }
      )
    compositeDisposable.add(loadMoreDisposable!!)
  }

  fun saveRepository(item: RepositoryItem) {
    compositeDisposable.add(
      repositoryManager
        .getAllRepositories()
        .flatMapCompletable { if (it.size >= 20) repositoryManager.delete(it.last()) else Completable.complete() }
        .andThen(repositoryManager.save(item.data.repository.copy(updatedInDb = System.currentTimeMillis())))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({}, {})
    )
  }

  private fun loadRepositories(): Single<List<RepositoryItem>> {
    val firstSingle = githubNetworkManager
      .search(query, currentPage)
      .subscribeOn(firstScheduler)
    val secondSingle = githubNetworkManager
      .search(query, currentPage)
      .subscribeOn(secondScheduler)

    return Single
      .zip(firstSingle, secondSingle,
           BiFunction<SearchResult, SearchResult, List<RepositoryItem>> { first, second ->
             convertToItems(first.items, second.items)
           })
      .delaySubscription(1, TimeUnit.SECONDS)
      .observeOn(AndroidSchedulers.mainThread())
  }

  private fun handleRepositoriesSuccess(list: List<RepositoryItem>) {
    listItemsLiveData.value = list
  }

  private fun handleRepositoriesFailure(throwable: Throwable) {
    errorLoadingEventLiveData.value = true
    handleErrorMessage(throwable)
  }

  private fun handleLoadMoreRepositoriesSuccess(list: List<RepositoryItem>) {
    val currentItems = listItemsLiveData.value ?: emptyList()
    listItemsLiveData.value = currentItems + list
  }

  private fun handleLoadMoreRepositoriesFailure(throwable: Throwable) {
    currentPage--
    errorLoadingEventLiveData.value = true
    handleErrorMessage(throwable)
  }

  private fun convertToItems(first: List<GithubRepository>, second: List<GithubRepository>): List<RepositoryItem> {
    return first.map { RepositoryItem(RepositoryItem.Data(generateId(it.id, true), it)) } +
      second.map { RepositoryItem(RepositoryItem.Data(generateId(it.id, false), it)) }
  }

  private fun generateId(originId: Int, isFirstStream: Boolean): Long {
    return originId.toLong() * 1000L + (if (isFirstStream) 1 else 2)
  }
}