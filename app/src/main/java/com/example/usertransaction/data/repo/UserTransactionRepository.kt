package com.example.usertransaction.data.repo

import com.example.usertransaction.data.model.UserTransaction
import kotlinx.coroutines.flow.Flow

interface UserTransactionRepository {
  fun getUserTransactions(): Flow<List<UserTransaction>>
  suspend fun refreshUserTransaction()
}