package com.test.project.ui.login

import android.content.Context
import android.content.Intent
import com.test.project.ui.common.BaseActivity
import com.test.project.ui.common.EmptyViewModel
import com.test.project.ui.login.auth.AuthFragment
import com.test.project.ui.main.MainActivity

class LoginActivity : BaseActivity<EmptyViewModel>(), AuthFragment.FragmentCommander {

  override val viewModelClass = EmptyViewModel::class.java
  override val rootFragment = AuthFragment.newInstance()

  override fun create() {}

  override fun onAuthSuccess() {
    startActivity(MainActivity.newInstance(this))
    finish()
  }

  companion object {
    fun newInstance(context: Context): Intent {
      return Intent(context.applicationContext, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
      }
    }
  }

}