package com.test.project.ui.login.auth

import androidx.lifecycle.MutableLiveData
import com.test.project.github.GithubConstants
import com.test.project.managers.UserManager
import com.test.project.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import java.io.OutputStreamWriter
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class AuthViewModel @Inject constructor(
  private val userManager: UserManager
) : BaseViewModel() {

  val loginResultEventLiveData = MutableLiveData<Boolean>()

  fun requestForAccessToken(code: String) {
    val grantType = "authorization_code"

    val postParams = "grant_type=" + grantType + "&code=" + code + "&redirect_uri=" + GithubConstants.REDIRECT_URI + "&client_id=" + GithubConstants.CLIENT_ID + "&client_secret=" + GithubConstants.CLIENT_SECRET
    GlobalScope.launch(Dispatchers.Default) {
      val url = URL(GithubConstants.TOKEN_URL)
      val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
      httpsURLConnection.requestMethod = "POST"
      httpsURLConnection.setRequestProperty("Accept", "application/json")
      httpsURLConnection.doInput = true
      httpsURLConnection.doOutput = true
      val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
      withContext(Dispatchers.IO) {
        outputStreamWriter.write(postParams)
        outputStreamWriter.flush()
      }
      val response = httpsURLConnection.inputStream.bufferedReader().use { it.readText() }
      withContext(Dispatchers.Main) {
        val jsonObject = JSONTokener(response).nextValue() as JSONObject
        val accessToken = jsonObject.getString("access_token")
        userManager.saveAuthorization(accessToken)
        loginResultEventLiveData.value = true
      }
    }
  }

}