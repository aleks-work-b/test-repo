package com.test.project.managers

import com.test.project.models.GithubRepository
import io.reactivex.Completable
import io.reactivex.Single

interface RepositoryManager {

  fun getAllRepositories(): Single<List<GithubRepository>>
  fun save(repo: GithubRepository): Completable
  fun delete(repo: GithubRepository): Completable

}