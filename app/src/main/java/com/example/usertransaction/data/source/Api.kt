package com.example.usertransaction.data.source

import com.example.usertransaction.data.model.UserTransaction
import com.example.usertransaction.data.model.WrapperUserTransactionResults
import retrofit2.http.GET

interface Api {
  @GET("entries")
  @WrapperUserTransactionResults
  suspend fun getUserTransactions(): List<UserTransaction>

}