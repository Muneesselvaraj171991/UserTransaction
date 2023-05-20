package com.example.usertransaction.data.source

import androidx.room.*
import com.example.usertransaction.data.model.UserTransaction
import com.example.usertransaction.data.model.UserTransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserTransactionDao {
   @Query(value = "SELECT * FROM `user-transactions`")
  fun getUserTransactions(): Flow<List<UserTransactionEntity>>

    @Insert()
    suspend fun insertUserTransactions(userTransactions: List<UserTransactionEntity>): List<Long>



}



