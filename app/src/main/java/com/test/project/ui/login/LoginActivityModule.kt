package com.test.project.ui.login

import android.app.Activity
import com.test.project.di.annotations.PerActivity
import com.test.project.di.annotations.PerFragment
import com.test.project.dialogs.AppProgressDialog
import com.test.project.dialogs.ProgressDialog
import com.test.project.ui.login.auth.AuthFragment
import com.test.project.ui.login.auth.AuthFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {

  @Binds
  @PerActivity
  abstract fun activity(activity: LoginActivity): Activity

  @PerFragment
  @ContributesAndroidInjector(modules = [AuthFragmentModule::class])
  abstract fun authFragmentInjector(): AuthFragment

  @Binds
  @PerActivity
  abstract fun bindsProgressDialog(dialog: AppProgressDialog): ProgressDialog

}