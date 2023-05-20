package com.example.usertransaction.data.source

import com.example.usertransaction.data.model.UserTransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
  @Provides
  fun providesAuthorDao(
    database: UserTransactionDatabase,
  ): UserTransactionDao = database.userTransactionDao()
}