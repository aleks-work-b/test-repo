package com.test.project.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.project.di.InjectingSavedStateViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : AppCompatDialogFragment(), HasAndroidInjector {

  @Inject
  lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

  @Inject
  lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

  lateinit var viewModel: VM
  protected abstract val viewModelClass: Class<VM>

  @get:LayoutRes
  protected abstract val layoutId: Int

  private var fragmentCommander: FragmentCommander? = null

  override fun androidInjector(): AndroidInjector<Any> {
    return childFragmentInjector
  }

  @CallSuper
  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    setupCommander(context)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(layoutId, container, false)
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)

    val factory = abstractViewModelFactory.create(this)
    viewModel = ViewModelProvider(this, factory)[viewModelClass]

    listenLiveData()
    onViewReady()
  }

  @CallSuper
  open fun listenLiveData() {
    listen(viewModel.errorMessageEventLiveData) { showError(it) }

    listen(viewModel.progressDialogEventLiveData) { show ->
      if (show) showProgressDialog() else hideProgressDialog()
    }
  }

  protected fun <D, T : MutableLiveData<D>> listen(liveData: T, observe: (D) -> Unit) {
    liveData.observe(this, Observer { it?.let { observe.invoke(it) } })
  }

  @CallSuper
  protected open fun setupCommander(context: Context) {
    if (context is FragmentCommander) {
      fragmentCommander = context
    }
  }

  protected abstract fun onViewReady()

  protected fun showProgressDialog() {
    fragmentCommander?.showProgressDialog()
  }

  protected fun hideProgressDialog() {
    fragmentCommander?.hideProgressDialog()
  }

  open fun showError(error: String?) {
    fragmentCommander?.showError(error)
  }

  interface FragmentCommander {
    fun showProgressDialog()
    fun hideProgressDialog()
    fun showError(error: String?)
  }
}