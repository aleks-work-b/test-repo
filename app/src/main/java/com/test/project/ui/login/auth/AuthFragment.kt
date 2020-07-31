package com.test.project.ui.login.auth

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.test.project.R
import com.test.project.github.GithubConstants
import com.test.project.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_login_auth.*
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection")
class AuthFragment : BaseFragment<AuthViewModel>() {

  override val viewModelClass = AuthViewModel::class.java
  override val layoutId = R.layout.fragment_login_auth

  private lateinit var githubAuthURLFull: String
  private lateinit var githubDialog: Dialog

  private var fragmentCommander: FragmentCommander? = null

  override fun setupCommander(context: Context) {
    super.setupCommander(context)

    if (context is FragmentCommander) {
      fragmentCommander = context
    }
  }

  override fun listenLiveData() {
    super.listenLiveData()

    listen(viewModel.loginResultEventLiveData) { handleLoginResult(it) }
  }

  override fun onViewReady() {
    val state = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())

    githubAuthURLFull =
      GithubConstants.AUTH_URL + "?client_id=" + GithubConstants.CLIENT_ID + "&scope=" + GithubConstants.SCOPE + "&redirect_uri=" + GithubConstants.REDIRECT_URI + "&state=" + state

    loginAuthActionLoginButton.setOnClickListener { actionLoginClick() }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setupGithubWebviewDialog(url: String) {
    val activity = activity ?: return
    githubDialog = Dialog(activity)
    val webView = WebView(activity)
    webView.isVerticalScrollBarEnabled = false
    webView.isHorizontalScrollBarEnabled = false
    webView.webViewClient = GithubWebViewClient()
    webView.settings.javaScriptEnabled = true
    webView.loadUrl(url)
    githubDialog.setContentView(webView)
    githubDialog.show()
  }

  private fun actionLoginClick() {
    setupGithubWebviewDialog(githubAuthURLFull)
  }

  private fun handleLoginResult(success: Boolean) {
    if (success) {
      fragmentCommander?.onAuthSuccess()
    }
  }

  interface FragmentCommander : BaseFragment.FragmentCommander {
    fun onAuthSuccess()
  }

  companion object {
    private const val TAG = "AuthFragment"

    fun newInstance(): BaseFragment<*> = AuthFragment()
  }

  @Suppress("OverridingDeprecatedMember")
  inner class GithubWebViewClient : WebViewClient() {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
      if (request!!.url.toString().startsWith(GithubConstants.REDIRECT_URI)) {
        handleUrl(request.url.toString())
        if (request.url.toString().contains("code=")) {
          githubDialog.dismiss()
        }
        return true
      }
      return false
    }

    private fun handleUrl(url: String) {
      val uri = Uri.parse(url)
      if (url.contains("code")) {
        val githubCode = uri.getQueryParameter("code") ?: ""
        viewModel.requestForAccessToken(githubCode)
      }
    }
  }

}