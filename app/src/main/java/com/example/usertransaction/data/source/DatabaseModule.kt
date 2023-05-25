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
  // val MIGRATION_1_2 : Migration = object : Migration(1, 2) {
  // override fun migrate(database: SupportSQLiteDatabase) {
  // database.execSQL(
  //    "ALTER TABLE user-transactions "
  //       + " ADD COLUMN last_update INTEGER"
  //  )
  //  }
  // }

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
