package com.test.project.network.service

import com.test.project.network.common.DataServiceFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object GithubNetworkModule {

  @Provides
  @Reusable
  fun provideGithubNetworkService(dataServiceFactory: DataServiceFactory): GithubNetworkService {
    return dataServiceFactory.create(GithubNetworkService::class.java)
  }

}