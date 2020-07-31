package com.test.project.database

import android.app.Application
import androidx.room.Room
import com.test.project.database.dao.GithubRepositoryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
  private const val DATABASE_NAME = "database-test"

  @Provides
  @Singleton
  fun provideDatabase(application: Application): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME)
      .build()
  }

  @Provides
  @Singleton
  fun provideGithubRepositoryDao(database: AppDatabase): GithubRepositoryDao = database.githubRepositoryDao()

}