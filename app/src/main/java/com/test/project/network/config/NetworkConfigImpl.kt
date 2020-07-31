package com.test.project.network.config

import com.test.project.network.common.NetworkConfig
import javax.inject.Inject

class NetworkConfigImpl @Inject constructor() : NetworkConfig {

  override fun getBaseUrl(): String {
    return "https://api.github.com/"
  }

}