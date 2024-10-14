package com.example.vpopoo.controller


import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CalculatorController {

    @GetMapping("/calculator")
    fun calculator(): String {
        return "Calculator"
    }

    @PostMapping("/calculate")
    fun calculate(model: Model,
        @RequestParam("num1") num1: Double,
        @RequestParam("num2") num2: Double,
        @RequestParam("operation") operation: String
    ): String {
        val result = when (operation) {
            "add" -> num1 + num2
            "subtract" -> num1 - num2
            "multiply" -> num1 * num2
            "divide" -> num1 / num2
            else -> throw IllegalArgumentException("Неизвестная операция")
        }
        model.addAttribute("result", result)
        return "Result"
    }
}