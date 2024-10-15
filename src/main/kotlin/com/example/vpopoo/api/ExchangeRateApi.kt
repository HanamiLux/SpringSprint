package com.example.vpopoo.api

import com.example.vpopoo.models.ExchangeRateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {

    @GET("v6/{apiKey}/latest/{baseCurrency}")
    fun getLatestRates(
        @Path("apiKey") apiKey: String,
        @Path("baseCurrency") baseCurrency: String
    ): Call<ExchangeRateResponse>
}