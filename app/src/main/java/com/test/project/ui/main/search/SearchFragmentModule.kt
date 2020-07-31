package com.test.project.ui.main.search

import androidx.fragment.app.Fragment
import com.test.project.di.annotations.PerFragment
import dagger.Binds
import dagger.Module

@Module
abstract class SearchFragmentModule {

  @Binds
  @PerFragment
  abstract fun fragment(fragment: SearchFragment): Fragment

}