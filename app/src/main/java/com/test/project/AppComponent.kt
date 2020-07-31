package com.test.project

import com.test.project.database.DatabaseModule
import com.test.project.di.CommonUiModule
import com.test.project.network.NetworkModule
import com.test.project.ui.ActivitiesModule
import com.test.project.ui.VMModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Suppress("unused")
@Singleton
@Component(
  modules = [
    CommonUiModule::class,
    AppModule::class,
    VMModule::class,
    ActivitiesModule::class,
    NetworkModule::class,
    DatabaseModule::class
  ]
)
interface AppComponent : AndroidInjector<App> {

  @Component.Factory
  abstract class AppFactory : AndroidInjector.Factory<App>

}