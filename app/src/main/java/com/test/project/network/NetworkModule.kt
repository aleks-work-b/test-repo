package com.test.project.network

import com.test.project.network.common.NetworkConfig
import com.test.project.network.config.NetworkConfigImpl
import com.test.project.network.service.GithubNetworkModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [GithubNetworkModule::class])
abstract class NetworkModule {

  @Binds
  @Singleton
  abstract fun bindsNetworkConfig(networkConfig: NetworkConfigImpl): NetworkConfig

  //  companion object {
  //    @Provides
  //    @Reusable
  //    fun refreshTokenService(dataServiceFactory: DataServiceFactory): RefreshTokenService {
  //      return dataServiceFactory.create(RefreshTokenServiceImpl::class.java)
  //    }
  //  }
}