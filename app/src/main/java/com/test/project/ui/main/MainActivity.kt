package com.test.project.ui.main

import android.content.Context
import android.content.Intent
import com.test.project.ext.addTransaction
import com.test.project.ui.common.BaseActivity
import com.test.project.ui.login.LoginActivity
import com.test.project.ui.main.history.HistoryFragment
import com.test.project.ui.main.search.SearchFragment

class MainActivity : BaseActivity<MainViewModel>(), SearchFragment.FragmentCommander,
                     HistoryFragment.FragmentCommander {

  override val viewModelClass = MainViewModel::class.java
  override val rootFragment = SearchFragment.newInstance()

  override fun shouldFinishBeforeCreate(): Boolean {
    return if (viewModel.isUserAuthorized()) {
      false
    } else {
      startActivity(LoginActivity.newInstance(this))
      false
    }
  }

  override fun create() {}

  override fun toHistoryFragment() {
    supportFragmentManager.addTransaction(HistoryFragment.newInstance(), containerResId, HistoryFragment.TAG)
  }

  override fun backClick() {
    onBackPressed()
  }

  companion object {
    fun newInstance(context: Context) = Intent(context.applicationContext, MainActivity::class.java)
  }

}