package com.example.usertransaction.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usertransaction.data.source.UserTransactionDao

@Database(
  entities = [
    UserTransactionEntity::class
  ],
  version = 1,
  exportSchema = true
)
abstract class UserTransactionDatabase : RoomDatabase() {
  abstract fun userTransactionDao(): UserTransactionDao
}
