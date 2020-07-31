package com.test.project.models

data class SearchResult(
  val totalCount: Int,
  val items: List<GithubRepository>
)