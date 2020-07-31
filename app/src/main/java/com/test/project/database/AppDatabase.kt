package com.test.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.project.database.dao.GithubRepositoryDao
import com.test.project.models.GithubRepository

@Database(
  entities = [GithubRepository::class],
  version = 1,
  exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun githubRepositoryDao(): GithubRepositoryDao
}