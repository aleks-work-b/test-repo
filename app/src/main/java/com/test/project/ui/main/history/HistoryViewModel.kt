package com.test.project.ui.main.history

import androidx.lifecycle.MutableLiveData
import com.test.project.adapters.RepositoryItem
import com.test.project.managers.RepositoryManager
import com.test.project.models.GithubRepository
import com.test.project.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
  private val repositoryManager: RepositoryManager
) : BaseViewModel() {

  val listItemsLiveData = MutableLiveData<List<RepositoryItem>>(emptyList())

  fun requestItems() {
    showProgressDialog()

    compositeDisposable.add(
      repositoryManager
        .getAllRepositories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ handleRepositoriesSuccess(it) }, { handleRepositoriesFailure(it) })
    )
  }

  private fun handleRepositoriesSuccess(list: List<GithubRepository>) {
    hideProgressDialog()

    listItemsLiveData.value = list.map { RepositoryItem(RepositoryItem.Data(it.id.toLong(), it)) }
  }

  private fun handleRepositoriesFailure(throwable: Throwable) {
    handleErrorMessage(throwable, true)
  }

}