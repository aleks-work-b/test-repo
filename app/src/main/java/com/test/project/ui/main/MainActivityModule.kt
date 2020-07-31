package com.test.project.ui.main

import android.app.Activity
import com.test.project.di.annotations.PerActivity
import com.test.project.di.annotations.PerFragment
import com.test.project.dialogs.AppProgressDialog
import com.test.project.dialogs.ProgressDialog
import com.test.project.ui.main.history.HistoryFragment
import com.test.project.ui.main.history.HistoryFragmentModule
import com.test.project.ui.main.search.SearchFragment
import com.test.project.ui.main.search.SearchFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

  @Binds
  @PerActivity
  abstract fun activity(activity: MainActivity): Activity

  @PerFragment
  @ContributesAndroidInjector(modules = [HistoryFragmentModule::class])
  abstract fun historyFragmentInjector(): HistoryFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
  abstract fun searchFragmentInjector(): SearchFragment

  @Binds
  @PerActivity
  abstract fun bindsProgressDialog(dialog: AppProgressDialog): ProgressDialog

}