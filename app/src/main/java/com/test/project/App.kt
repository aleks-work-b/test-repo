package com.test.project

import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : MultiDexApplication(), HasAndroidInjector {

  @Inject
  lateinit var activityInjector: DispatchingAndroidInjector<Any>

  override fun onCreate() {
    super.onCreate()

    DaggerAppComponent
      .factory()
      .create(this)
      .inject(this)
  }

  override fun androidInjector(): AndroidInjector<Any> {
    return activityInjector
  }

}