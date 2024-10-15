package com.example.vpopoo.controller

import com.example.vpopoo.api.ApiClient
import com.example.vpopoo.models.ExchangeRateResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

@Controller
class CurrencyConverterController {

    val apiKey = "233f69f4a7f916292c383b3b"

    @GetMapping("/currency-converter")
    fun currencyConverter(model: Model): CompletableFuture<String> {
        val future = CompletableFuture<String>()
        val call = ApiClient.exchangeRateApi.getLatestRates(apiKey, "USD")

        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(
                call: Call<ExchangeRateResponse>,
                response: Response<ExchangeRateResponse>
            ) {
                if (response.isSuccessful) {
                    val exchangeRateResponse = response.body()
                    exchangeRateResponse?.let {
                        val currencyCodes = it.conversionRates.keys
                        model.addAttribute("currencyCodes", currencyCodes)
                        future.complete("Currency-converter")
                    }
                } else {
                    future.completeExceptionally(RuntimeException("Error: ${response.errorBody()}"))
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                future.completeExceptionally(t)
            }
        })

        return future
    }

    @PostMapping("/convert")
    fun convert(
        @RequestParam("fromCurrency") fromCurrency: String,
        @RequestParam("toCurrency") toCurrency: String,
        @RequestParam("amount") amount: Double,
        model: Model
    ): CompletableFuture<String> {
        val future = CompletableFuture<String>()
            val call = ApiClient.exchangeRateApi.getLatestRates(apiKey, fromCurrency)

            call.enqueue(object : Callback<ExchangeRateResponse> {
                override fun onResponse(
                    call: Call<ExchangeRateResponse>,
                    response: Response<ExchangeRateResponse>
                ) {
                    if (response.isSuccessful) {
                        val exchangeRateResponse = response.body()
                        exchangeRateResponse?.let {
                        val conversionRate = it.conversionRates[toCurrency]!!
                            val result = amount * conversionRate
                            model.addAttribute("result", result)
                            future.complete("Conversion-result")
                        }
                    } else {
                        future.completeExceptionally(RuntimeException("Error: ${response.errorBody()}"))
                    }
                }

                override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                    future.completeExceptionally(t)
                }
            })


        return future
    }
}