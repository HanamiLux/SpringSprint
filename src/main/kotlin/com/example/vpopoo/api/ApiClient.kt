package com.example.vpopoo.api

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://v6.exchangerate-api.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    val exchangeRateApi: ExchangeRateApi by lazy {
        retrofit.create(ExchangeRateApi::class.java)
    }
}