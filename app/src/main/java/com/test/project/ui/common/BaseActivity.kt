package com.test.project.ui.common

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.project.R
import com.test.project.di.InjectingSavedStateViewModelFactory
import com.test.project.dialogs.ProgressDialog
import com.test.project.ext.replaceTransaction
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), HasAndroidInjector,
                                                  BaseFragment.FragmentCommander {

  @Inject
  lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

  @Inject
  lateinit var progressDialog: ProgressDialog

  abstract val viewModelClass: Class<VM>

  lateinit var viewModel: VM

  @get:IdRes
  protected open val containerResId: Int = R.id.container_layout

  @get:LayoutRes
  protected open val layoutResId: Int = R.layout.activity_base

  protected abstract val rootFragment: Fragment?

  override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

  final override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)

    super.onCreate(savedInstanceState)

    val factory = abstractViewModelFactory.create(this)
    viewModel = ViewModelProvider(this, factory)[viewModelClass]

    if (shouldFinishBeforeCreate()) {
      finish()
      return
    }

    setContentView(layoutResId)
    listenLiveData()
    create()

    if (savedInstanceState == null) {
      rootFragment?.let { setRootFragment(it) }
    }
  }

  protected abstract fun create()

  open fun listenLiveData() {
    listen(viewModel.errorMessageEventLiveData) { showError(it) }

    listen(viewModel.progressDialogEventLiveData) { show ->
      if (show) showProgressDialog() else hideProgressDialog()
    }
  }

  protected open fun shouldFinishBeforeCreate(): Boolean = false

  private fun setRootFragment(rootFragment: Fragment) {
    supportFragmentManager.replaceTransaction(rootFragment, containerResId)
  }

  protected fun <D, T : MutableLiveData<D>> listen(liveData: T, observe: (D) -> Unit) {
    liveData.observe(this, Observer { it?.let { observe.invoke(it) } })
  }

  override fun showProgressDialog() {
    progressDialog.show()
  }

  override fun hideProgressDialog() {
    progressDialog.hide()
  }

  override fun showError(error: String?) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
  }

}