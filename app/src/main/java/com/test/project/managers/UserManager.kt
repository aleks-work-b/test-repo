package com.test.project.managers

interface UserManager {

  fun isUserAuthorized(): Boolean
  fun saveAuthorization(accessToken: String)

}