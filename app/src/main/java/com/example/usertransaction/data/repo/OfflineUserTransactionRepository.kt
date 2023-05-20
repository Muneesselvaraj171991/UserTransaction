package com.example.usertransaction.data.repo

import com.example.usertransaction.data.model.*
import com.example.usertransaction.data.source.Api
import com.example.usertransaction.data.source.UserTransactionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OfflineUserTransactionRepository @Inject constructor(
    private val dao: UserTransactionDao,
    private val api: Api
) : UserTransactionRepository {

  override fun getUserTransactions(): Flow<List<UserTransaction>> {
    return dao.getUserTransactions().map { entityUserTransactions ->
      entityUserTransactions.map(UserTransactionEntity::asExternalModel)
    }.onEach {
      if (it.isEmpty()) {
        refreshUserTransaction()
      }
    }
  }

  override suspend fun refreshUserTransaction() {
    api.getUserTransactions()
      .shuffled()
      .also { externalMovies ->
        dao.insertUserTransactions(userTransactions = externalMovies.map(UserTransaction::asEntity))
      }
  }



}