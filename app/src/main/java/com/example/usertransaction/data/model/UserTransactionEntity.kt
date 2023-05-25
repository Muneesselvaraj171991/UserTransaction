package com.example.usertransaction.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user-transactions")
data class UserTransactionEntity(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "username")
  val username: String,
  @ColumnInfo(name = "description")
  val description: String

)

fun UserTransactionEntity.asExternalModel() = UserTransaction(
  username = username,
  description = description
)
