package com.example.vpopoo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CurrencyConverterController {

    @GetMapping("/currency-converter")
    fun currencyConverter(): String {
        return "Currency-converter"
    }

    @PostMapping("/convert")
    fun convert(
        @RequestParam("fromCurrency") fromCurrency: String,
        @RequestParam("toCurrency") toCurrency: String,
        @RequestParam("amount") amount: Double,
        model: Model
    ): String {
        //TODO Логика конвертации
        val conversionRate = 1.0 // Заглушка
        val result = amount * conversionRate
        model.addAttribute("result", result)
        return "Conversion-result"
    }
}