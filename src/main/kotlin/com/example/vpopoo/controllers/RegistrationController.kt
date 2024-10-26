package com.example.vpopoo.controllers

import com.example.vpopoo.model.UserModel
import com.example.vpopoo.service.UserApiService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class RegistrationController @Autowired constructor(
    private val userApiService: UserApiService
) {

    @GetMapping("/register")
    fun showRegistrationForm(@RequestParam(value = "error", required = false) error: String?, model: Model): String {
        model.addAttribute("user", UserModel())
        if (error != null) {
            val message = when (error) {
                "invalid_fields" -> "Есть неправильно заполненные поля"
                "validation_error" -> "Слишком короткий пароль (< 4)"
                "empty_fields_error" -> "Есть пустые поля"
                "user_exists" -> "Пользователь с таким логином уже существует"
                "registration_error" -> "Ошибка при сохранении данных"
                else -> "Неизвестная ошибка"
            }
            model.addAttribute("message", message)
        }
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@Valid @ModelAttribute user: UserModel, bindingResult: BindingResult, model: Model): String {
        return try {
            if (bindingResult.hasErrors()) {
                "redirect:/register?error=invalid_fields"
            } else if (userApiService.getUserByName(user.username) != null) {
                "redirect:/register?error=user_exists"
            } else {
                userApiService.addOrUpdateUser(user, apiUrl1 = "http://localhost:8081/api/register")
                "redirect:/login"
            }
        } catch (e: Exception) {
            when {
                e.message!!.contains("\"empty_fields_error\"") -> "redirect:/register?error=empty_fields_error"
                e.message!!.contains("\"validation_error\"") -> "redirect:/register?error=validation_error"
                else -> "redirect:/register?error=registration_error"
            }
        }
    }
}
