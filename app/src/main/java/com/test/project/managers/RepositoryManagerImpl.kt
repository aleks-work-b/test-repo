package com.test.project.managers

import com.test.project.database.dao.GithubRepositoryDao
import com.test.project.models.GithubRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RepositoryManagerImpl @Inject constructor(
  private val githubRepositoryDao: GithubRepositoryDao
) : RepositoryManager {

  override fun getAllRepositories(): Single<List<GithubRepository>> {
    return githubRepositoryDao.getAll()
  }

  override fun save(repo: GithubRepository): Completable {
    return githubRepositoryDao.save(repo)
  }

  override fun delete(repo: GithubRepository): Completable {
    return githubRepositoryDao.delete(repo)
  }

}