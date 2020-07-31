package com.test.project.network.service

import com.test.project.models.GithubRepository
import com.test.project.models.SearchResult
import com.test.project.preferences.UserPreferenceManager
import io.reactivex.Single
import javax.inject.Inject

class GithubNetworkManager @Inject constructor(
  private val userPreferenceManager: UserPreferenceManager,
  private val githubNetworkService: GithubNetworkService
) {

  fun search(name: String, page: Int = 3): Single<SearchResult> {
    var token = userPreferenceManager.accessToken ?: ""
    if (token.isNotEmpty()) {
      token = "token $token"
    }
    return githubNetworkService
      .search(token, name, page = page)
      .map {
        SearchResult(
          totalCount = it.totalCount!!,
          items = it.items?.map { repositoryResponse ->
            GithubRepository(
              id = repositoryResponse.id!!,
              name = repositoryResponse.name!!,
              fullName = repositoryResponse.fullName!!,
              htmlUrl = repositoryResponse.htmlUrl!!,
              description = repositoryResponse.description!!,
              url = repositoryResponse.url!!,
              stargazersCount = repositoryResponse.stargazersCount!!,
              score = repositoryResponse.score!!
            )
          } ?: emptyList<GithubRepository>()
        )
      }
  }

}