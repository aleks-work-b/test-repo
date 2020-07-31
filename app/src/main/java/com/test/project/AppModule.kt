package com.test.project

import android.app.Application
import com.test.project.managers.RepositoryManager
import com.test.project.managers.RepositoryManagerImpl
import com.test.project.managers.UserManager
import com.test.project.managers.UserManagerImpl
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [AndroidInjectionModule::class])
abstract class AppModule {

  @Binds
  @Singleton
  abstract fun application(app: App): Application

  @Binds
  @Singleton
  abstract fun bindsUserManager(manager: UserManagerImpl): UserManager

  @Binds
  @Singleton
  abstract fun bindsRepositoryManager(manager: RepositoryManagerImpl): RepositoryManager

}