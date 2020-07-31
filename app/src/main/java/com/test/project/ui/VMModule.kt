package com.test.project.ui

import androidx.lifecycle.ViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import com.test.project.di.annotations.ViewModelKey
import com.test.project.ui.common.EmptyViewModel
import com.test.project.ui.login.auth.AuthViewModel
import com.test.project.ui.main.MainViewModel
import com.test.project.ui.main.history.HistoryViewModel
import com.test.project.ui.main.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@AssistedModule
@Module(includes = [AssistedInject_VMModule::class])
abstract class VMModule {

  @Binds
  @IntoMap
  @ViewModelKey(EmptyViewModel::class)
  abstract fun bindsEmptyViewModel(vm: EmptyViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(AuthViewModel::class)
  abstract fun bindsAuthViewModel(vm: AuthViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SearchViewModel::class)
  abstract fun bindsSearchViewModel(vm: SearchViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(HistoryViewModel::class)
  abstract fun bindsHistoryViewModel(vm: HistoryViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindsMainViewModel(vm: MainViewModel): ViewModel

}
