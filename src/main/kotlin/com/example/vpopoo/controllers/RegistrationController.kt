package com.example.vpopoo.controllers

import com.example.vpopoo.model.UserModel
import com.example.vpopoo.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class RegistrationController @Autowired constructor(
    private val userService: UserService
) {
    @GetMapping("/register")
    fun showRegistrationForm(model: Model): String {
        model.addAttribute("user", UserModel())
        return "register"
    }

    @PostMapping("/register")
    fun registerUser(@Valid @ModelAttribute("user") user: UserModel, bindingResult: BindingResult): String {
        try {
            if(bindingResult.hasErrors()) {
                return "register"
            }
            // Проверка на уникальность имени пользователя и электронной почты
            if (userService.getUserByName(user.username) != null) {
                // Возврат сообщения об ошибке, если пользователь уже существует
                return "redirect:/register?error=user_exists"
            }
            userService.registerUser(user)
            return "redirect:/login"
        } catch (e: Exception) {
            // Возврат сообщения об ошибке, если произошла ошибка при сохранении данных
            return "redirect:/register?error=registration_error"
        }
    }
}