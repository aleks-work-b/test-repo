package com.test.project.ui.login.auth

import androidx.fragment.app.Fragment
import com.test.project.di.annotations.PerFragment
import dagger.Binds
import dagger.Module

@Module
abstract class AuthFragmentModule {

  @Binds
  @PerFragment
  abstract fun fragment(fragment: AuthFragment): Fragment

}