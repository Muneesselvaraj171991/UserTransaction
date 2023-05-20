package com.example.usertransaction.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserTransaction(
  @Json(name = "Category")
  val username: String
)

fun UserTransaction.asEntity() = UserTransactionEntity(
  username = username,
)
