package com.test.project.network.common

import com.test.project.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
@Singleton
class DataServiceFactory @Inject internal constructor(
  private val networkConfig: NetworkConfig
) {

  fun <T> create(serviceClass: Class<T>): T {
    val okHttpClientBuilder = okHttpClientBuilder()
    addLoggerInterceptors(okHttpClientBuilder)
    return retrofit(okHttpClientBuilder).create(serviceClass)
  }

  private fun okHttpClientBuilder(): OkHttpClient.Builder {
    return OkHttpClient.Builder()
      .connectTimeout(
        TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS
      )
      .writeTimeout(
        TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS
      )
      .readTimeout(
        TIMEOUT_READ.toLong(), TimeUnit.SECONDS
      )
  }

  private fun addLoggerInterceptors(builder: OkHttpClient.Builder) {
    if (BuildConfig.DEBUG) {
      builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }
  }

  private fun retrofit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit {
    return Retrofit.Builder()
      .baseUrl(networkConfig.getBaseUrl())
      .client(okHttpClientBuilder.build())
      .addCallAdapterFactory(
        RxErrorHandlingCallAdapterFactory.create()
      )
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  companion object {
    private const val TIMEOUT_CONNECT = 10
    private const val TIMEOUT_WRITE = 10
    private const val TIMEOUT_READ = 10
  }

}