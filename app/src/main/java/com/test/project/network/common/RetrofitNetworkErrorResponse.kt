package com.test.project.network.common

import com.google.gson.annotations.SerializedName

class RetrofitNetworkErrorResponse(
  @SerializedName("statusCode")
  val statusCode: Int?,
  @SerializedName("message")
  val message: String?,
  @SerializedName("error")
  val error: String?
)