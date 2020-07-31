package com.test.project.database.dao

import androidx.room.*
import com.test.project.models.GithubRepository
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GithubRepositoryDao {

  @Query("SELECT * FROM $TABLE_NAME ORDER BY updatedInDb DESC")
  fun getAll(): Single<List<GithubRepository>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(model: GithubRepository): Completable

  @Delete
  fun delete(model: GithubRepository): Completable

  companion object {
    private const val TABLE_NAME = "GithubRepository"
  }

}