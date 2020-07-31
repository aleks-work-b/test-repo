package com.test.project.ui

import com.test.project.di.annotations.PerActivity
import com.test.project.ui.login.LoginActivity
import com.test.project.ui.login.LoginActivityModule
import com.test.project.ui.main.MainActivity
import com.test.project.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivitiesModule {

  @PerActivity
  @ContributesAndroidInjector(modules = [LoginActivityModule::class])
  abstract fun loginActivityInjector(): LoginActivity

  @PerActivity
  @ContributesAndroidInjector(modules = [MainActivityModule::class])
  abstract fun mainActivityInjector(): MainActivity

}