package api.vpopooapi.controllers

import api.vpopooapi.model.UserModel
import api.vpopooapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/register")
class RegistrationController @Autowired constructor(
    private val userService: UserService
) {

    @GetMapping
    fun showRegistrationForm(@RequestParam(value = "error", required = false) error: String?): ResponseEntity<Map<String, Any>> {
        val response = mutableMapOf<String, Any>()
        response["user"] = UserModel()
        if (error != null) {
            response["message"] = when (error) {
                "invalid_fields" -> "Есть неправильно заполненные поля"
                "validation_error" -> "Слишком короткий пароль (< 4)"
                "empty_fields_error" -> "Есть пустые поля"
                "user_exists" -> "Пользователь с таким логином уже существует"
                "registration_error" -> "Ошибка при сохранении данных"
                else -> "Неизвестная ошибка"
            }
        }
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun registerUser(@RequestBody user: UserModel, bindingResult: BindingResult): ResponseEntity<Any> {
        return try {
            if (bindingResult.hasErrors()) {
                ResponseEntity.badRequest().body("invalid_fields")
            } else if (userService.getUserByName(user.username) != null) {
                ResponseEntity.badRequest().body("user_exists")
            } else {
                userService.registerUser(user)
                ResponseEntity.ok(user)
            }
        } catch (e: Exception) {
            val errorMessage = when {
                e.message!!.contains("Invalid user data") -> "empty_fields_error"
                e.message!!.contains("Invalid password") -> "validation_error"
                else -> "registration_error"
            }
            ResponseEntity.badRequest().body(errorMessage)
        }
    }
}
