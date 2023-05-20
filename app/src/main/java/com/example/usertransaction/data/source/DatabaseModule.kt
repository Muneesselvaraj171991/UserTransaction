package com.example.usertransaction.data.source

import android.content.Context
import androidx.room.Room
import com.example.usertransaction.data.model.UserTransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Provides
  @Singleton
  fun providesDatabase(
    @ApplicationContext context: Context,
  ): UserTransactionDatabase = Room.databaseBuilder(
    context,
    UserTransactionDatabase::class.java,
    "user-transactions-database"
  ).build()
}
