package com.test.project.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

  val errorMessageEventLiveData = MutableLiveData<String>()
  val progressDialogEventLiveData = MutableLiveData<Boolean>()

  protected val compositeDisposable = CompositeDisposable()

  protected open fun handleErrorMessage(throwable: Throwable, hideProgressDialog: Boolean = false) {
    handleErrorMessage(throwable.message, hideProgressDialog)
  }

  protected open fun handleErrorMessage(error: String?, hideProgressDialog: Boolean = true) {
    errorMessageEventLiveData.value = error ?: ""

    if (hideProgressDialog) {
      hideProgressDialog()
    }
  }

  protected fun showProgressDialog() {
    progressDialogEventLiveData.value = true
  }

  protected fun hideProgressDialog() {
    progressDialogEventLiveData.value = false
  }

  @CallSuper
  override fun onCleared() {
    compositeDisposable.clear()

    super.onCleared()
  }

}