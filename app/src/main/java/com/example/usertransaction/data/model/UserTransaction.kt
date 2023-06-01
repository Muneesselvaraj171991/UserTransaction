package com.example.usertransaction.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserTransaction(
  @Json(name = "Category")
  val username: String,
  @Json(name = "Description")
  val description: String
  )

fun UserTransaction.asEntity() = UserTransactionEntity(
  username = username,
  description = description
)
