package com.test.project.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class UserPreferenceManager @Inject constructor(app: Application) {

  private val preferences: SharedPreferences = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

  var isAuthorized: Boolean
    get() = preferences.getBoolean(KEY_IS_AUTHORIZED, false)
    set(value) = preferences.edit().putBoolean(KEY_IS_AUTHORIZED, value).apply()

  var accessToken: String?
    get() = preferences.getString(KEY_ACCESS_TOKEN, "")
    set(value) = preferences.edit().putString(KEY_ACCESS_TOKEN, value).apply()

  companion object {
    private const val PREF_NAME = "user_pref"

    private const val KEY_IS_AUTHORIZED = "key_is_authorized"
    private const val KEY_ACCESS_TOKEN = "key_access_token"
  }

}