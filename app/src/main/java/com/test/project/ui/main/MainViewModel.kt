package com.test.project.ui.main

import com.test.project.managers.UserManager
import com.test.project.ui.common.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
  private val userManager: UserManager
) : BaseViewModel() {

  fun isUserAuthorized() = userManager.isUserAuthorized()
}