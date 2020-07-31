package com.test.project.network.service

import com.test.project.network.dto.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubNetworkService {

  @GET("search/repositories")
  fun search(
    @Header("Authorization") authorization: String,
    @Query("q") name: String,
    @Query("sort") sort: String = "stars",
    @Query("order") order: String = "desc",
    @Query("per_page") limit: Int = 15,
    @Query("page") page: Int = 2
  ): Single<SearchResponse>

}