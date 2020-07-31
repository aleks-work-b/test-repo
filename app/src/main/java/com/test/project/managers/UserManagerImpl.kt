package com.test.project.managers

import com.test.project.preferences.UserPreferenceManager
import javax.inject.Inject

class UserManagerImpl @Inject constructor(
  private val userPreferenceManager: UserPreferenceManager
) : UserManager {

  override fun isUserAuthorized() = userPreferenceManager.isAuthorized

  override fun saveAuthorization(accessToken: String) {
    userPreferenceManager.accessToken = accessToken
    userPreferenceManager.isAuthorized = true
  }

}