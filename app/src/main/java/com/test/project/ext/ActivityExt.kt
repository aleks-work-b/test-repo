package com.test.project.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replaceTransaction(fragment: Fragment, @IdRes containerResId: Int, tag: String? = null) {
  beginTransaction()
    .replace(containerResId, fragment, tag)
    .commitAllowingStateLoss()
}

fun FragmentManager.addTransaction(fragment: Fragment, @IdRes containerResId: Int, tag: String? = null) {
  beginTransaction()
    .add(containerResId, fragment, tag)
    .addToBackStack(tag)
    .commitAllowingStateLoss()
}