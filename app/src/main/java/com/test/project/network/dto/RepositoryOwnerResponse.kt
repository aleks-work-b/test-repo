package com.test.project.network.dto

import com.google.gson.annotations.SerializedName

data class RepositoryOwnerResponse(
  @SerializedName("login")
  private val login: String?,
  @SerializedName("id")
  private val id: Int?,
  @SerializedName("node_id")
  private val nodeId: String?,
  @SerializedName("avatar_url")
  private val avatarUrl: String?,
  @SerializedName("gravatar_id")
  private val grAvatarId: String?,
  @SerializedName("url")
  private val url: String?,
  @SerializedName("received_events_url")
  private val receivedEventsUrl: String?,
  @SerializedName("type")
  private val type: String?
)