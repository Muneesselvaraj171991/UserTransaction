package com.example.usertransaction.data.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

data class UserTransactionResults(
  val entries: List<UserTransaction>
)

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class WrapperUserTransactionResults

class UserTransactionsJsonConverter {
  @WrapperUserTransactionResults
  @FromJson
  fun fromJson(json: UserTransactionResults): List<UserTransaction> {
    return json.entries
  }

  @ToJson
  fun toJson(@WrapperUserTransactionResults value: List<UserTransaction>): UserTransactionResults {
    throw UnsupportedOperationException()
  }
}