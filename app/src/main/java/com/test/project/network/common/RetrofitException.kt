package com.test.project.network.common

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

@Suppress("unused", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RetrofitException private constructor(
  message: String?,
  val url: String?,
  private val response: Response<*>?,
  val kind: Kind,
  exception: Throwable?,
  private val retrofit: Retrofit?
) : RuntimeException(message, exception) {
  val code: Int
    get() = response!!.code()

  /**
   * HTTP response body converted to specified `type`. `null` if there is no response.
   *
   * @throws IOException if unable to convert the body to the specified `type`.
   */
  @Throws(IOException::class)
  fun <T> getErrorBodyAs(type: Class<T>?): T? {
    val retrofit = retrofit ?: return null
    val response = response ?: return null
    val responseBody = response.errorBody() ?: return null
    val converter: Converter<ResponseBody, T> = retrofit.responseBodyConverter(type, arrayOfNulls(0))
    return converter.convert(responseBody)
  }

  fun <T> tryGetErrorBodyAs(type: Class<T>?): T? {
    val retrofit = retrofit ?: return null
    val response = response ?: return null
    val responseBody = response.errorBody() ?: return null
    val converter: Converter<ResponseBody, T> = retrofit.responseBodyConverter(type, arrayOfNulls(0))
    return try {
      converter.convert(responseBody)
    } catch (throwable: Throwable) {
      Log.e(TAG, Log.getStackTraceString(throwable))
      null
    }
  }

  /**
   * Identifies the event kind which triggered a [RetrofitException].
   */
  enum class Kind {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-200 HTTP status code was received from the server.
     */
    HTTP,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to re-throw this
     * exception so your application crashes.
     */
    UNEXPECTED
  }

  companion object {
    private const val TAG = "RetrofitException"

    fun httpError(url: String?, response: Response<*>, retrofit: Retrofit?): RetrofitException {
      val message = response.code().toString() + " " + response.message()
      return RetrofitException(
        message, url, response, Kind.HTTP, null,
        retrofit
      )
    }

    @JvmStatic
    fun networkError(exception: IOException): RetrofitException {
      return RetrofitException(
        exception.message, null, null,
        Kind.NETWORK, exception,
        null
      )
    }

    @JvmStatic
    fun unexpectedError(exception: Throwable): RetrofitException {
      return RetrofitException(
        exception.message, null, null,
        Kind.UNEXPECTED, exception,
        null
      )
    }
  }

}