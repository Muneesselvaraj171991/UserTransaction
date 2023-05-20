package com.example.usertransaction

import com.example.usertransaction.data.model.UserTransactionsJsonConverter
import com.example.usertransaction.data.source.Api
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val API_URL = "https://api.publicapis.org/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  fun provideApi(): Api {

    val authQueryAppenderInterceptor = Interceptor { chain ->
      val requestBuilder = chain.request().newBuilder()

      val url = chain.request().url
      val urlBuilder = url.newBuilder()
      chain.proceed(
        requestBuilder
          .url(urlBuilder.build())
          .build()
      )
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpBuilder = OkHttpClient.Builder()
      .addInterceptor(authQueryAppenderInterceptor)
      .addInterceptor(loggingInterceptor)

    val moshi = Moshi.Builder()
      .add(UserTransactionsJsonConverter())
      .addLast(KotlinJsonAdapterFactory())
      .build()

    return Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .client(okHttpBuilder.build())
      .build()
      .create(Api::class.java)
  }
}