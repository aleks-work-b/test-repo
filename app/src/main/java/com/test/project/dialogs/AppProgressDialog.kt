package com.test.project.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.core.content.ContextCompat
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.Circle
import com.test.project.R
import com.test.project.di.annotations.PerActivity
import javax.inject.Inject

@PerActivity
class AppProgressDialog @Inject constructor(
  private val activity: Activity
) : ProgressDialog {

  private val dialog: Dialog
  private var loaderCounter = 0
  private var localProgressDialogListener: LocalProgressDialogListener? = null
  private val spinKitView: SpinKitView

  init {
    if (activity is LocalProgressDialogListener) {
      localProgressDialogListener = activity
    }

    dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    val window = dialog.window
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    spinKitView = SpinKitView(activity)

    spinKitView.setColor(ContextCompat.getColor(activity, R.color.teal_700))
    val foldingCube = Circle()
    spinKitView.setIndeterminateDrawable(foldingCube)
  }

  @Synchronized
  override fun show() {
    if (loaderCounter == 0) {
      showDialog()
    }
    loaderCounter++
  }

  val isDialogShowed: Boolean = loaderCounter != 0

  @Synchronized
  override fun hide() {
    if (loaderCounter == 0) {
      return
    }

    if (loaderCounter == 1) {
      hideDialog()
    }

    loaderCounter--
  }

  private fun showDialog() {
    if (activity.isDestroyed) {
      throw RuntimeException("activity isDestroyed")
    }

    dialog.setContentView(spinKitView)
    dialog.show()

    localProgressDialogListener?.onProgressDialogShow()
  }

  private fun hideDialog() {
    try {
      dialog.dismiss()
      localProgressDialogListener?.onProgressDialogDismiss()
    } catch (e: Exception) {
    }
  }

  interface LocalProgressDialogListener {
    fun onProgressDialogShow()
    fun onProgressDialogDismiss()
  }

  companion object {
    private const val TAG = "ProgressDialog"
  }
}