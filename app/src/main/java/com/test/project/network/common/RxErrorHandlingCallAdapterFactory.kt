package com.test.project.network.common

import com.test.project.network.common.RetrofitException.Companion.networkError
import com.test.project.network.common.RetrofitException.Companion.unexpectedError
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
  private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

  @Suppress("UNCHECKED_CAST")
  override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {
    return RxCallAdapterWrapper<Any>(
      retrofit, original[returnType, annotations, retrofit] as CallAdapter<Any, Any>?
    )
  }

  private class RxCallAdapterWrapper<R> internal constructor(
    private val retrofit: Retrofit,
    private val wrapped: CallAdapter<R, Any>?
  ) : CallAdapter<R, Any> {
    override fun responseType(): Type {
      return wrapped!!.responseType()
    }

    @Suppress("UNCHECKED_CAST")
    override fun adapt(call: Call<R>): Any {
      val result = wrapped!!.adapt(call)

      return when (result) {
        is Single<*> -> (result as Single<R>).onErrorResumeNext { Single.error(asRetrofitException(it)) }
        is Observable<*> -> {
          return (result as Observable<R>).onErrorResumeNext { throwable: Throwable ->
            Observable.error(asRetrofitException(throwable))
          }
        }
        is Completable -> result.onErrorResumeNext { throwable: Throwable ->
          Completable.error(
            asRetrofitException(throwable)
          )
        }
        else -> result
      }

      //      if (result is Single<*>) {
      //        return (result as Single<R>).onErrorResumeNext { Single.error(asRetrofitException(it)) }
      //      }
      //      if (result is Observable<*>) {
      //        return (result as Observable<R>).onErrorResumeNext { throwable: Throwable ->
      //          Observable.error(asRetrofitException(throwable))
      //        }
      //      }
      //      return if (result is Completable) {
      //        result.onErrorResumeNext { throwable: Throwable -> Completable.error(asRetrofitException(throwable)) }
      //      } else result
    }

    private fun asRetrofitException(throwable: Throwable): RetrofitException {
      // We had non-200 http error
      if (throwable is HttpException) {
        val response = throwable.response()
        return RetrofitException.httpError(response!!.raw().request.url.toString(), response, retrofit)
      }

      // A network error happened
      if (throwable is IOException) {
        return networkError(throwable)
      }

      // We don't know what happened. We need to simply convert to an unknown error
      return unexpectedError(throwable)
    }

  }

  companion object {
    fun create(): CallAdapter.Factory =
      RxErrorHandlingCallAdapterFactory()
  }

}