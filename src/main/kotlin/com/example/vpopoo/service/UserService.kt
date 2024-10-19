package com.example.vpopoo.service

import com.example.vpopoo.model.RoleEnum
import com.example.vpopoo.model.UserModel
import com.example.vpopoo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun registerUser(user: UserModel) {
        try {
            // Валидация данных
            if (user.username!!.isEmpty() || user.password!!.isEmpty()) {
                throw Exception("Invalid user data")
            }
            user.isActive = true
            user.password = passwordEncoder.encode(user.password)
            user.roles = Collections.singleton(RoleEnum.USER)
            userRepository.save(user)
        } catch (e: Exception) {
            // Обработка исключений
            throw Exception("Error registering user: ${e.message}")
        }
    }

    fun getUserByName(name: String): UserModel? {
        try {
            // Возвращаем пользователя по имени
            return userRepository.findByUsername(name)
        } catch (e: Exception) {
            // Обработка исключений
            throw Exception("Error getting user by name: ${e.message}")
        }
    }
}