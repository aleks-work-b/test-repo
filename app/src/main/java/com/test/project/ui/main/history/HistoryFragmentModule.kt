package com.test.project.ui.main.history

import androidx.fragment.app.Fragment
import com.test.project.di.annotations.PerFragment
import dagger.Binds
import dagger.Module

@Module
abstract class HistoryFragmentModule {

  @Binds
  @PerFragment
  abstract fun fragment(fragment: HistoryFragment): Fragment

}