package com.test.project.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GithubRepository(
  @PrimaryKey
  val id: Int,
  val name: String,
  val fullName: String,
  val htmlUrl: String,
  val description: String,
  val url: String,
  val stargazersCount: Int,
  val score: Double,
  var updatedInDb: Long = System.currentTimeMillis()
)