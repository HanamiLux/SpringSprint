package api.vpopooapi.service

import api.vpopooapi.model.RoleEnum
import api.vpopooapi.model.UserModel
import api.vpopooapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.mindrot.jbcrypt.BCrypt
import java.util.*

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun registerUser(user: UserModel): UserModel {
        try {
            // Валидация данных
            if (user.username.isEmpty() || user.password.isEmpty()) {
                throw Exception("Invalid user data")
            }
            if (user.password.length < 4) {
                throw Exception("Invalid password")
            }
            user.isActive = true
            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt(8))
            user.roles = Collections.singleton(RoleEnum.MANAGER)
            userRepository.save(user)
            return user
        } catch (e: Exception) {
            throw Exception("Error registering user: ${e.message}")
        }
    }

    fun getAllUsers(): List<UserModel> = userRepository.findAll()

    fun updateUser(id: Long, user: UserModel): UserModel? {
        return if (userRepository.existsById(id)) {
            user.id = id
            userRepository.save(user)
        } else {
            null
        }
    }

    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
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

    fun getUserById(id: Long): UserModel? {
        try {
            return userRepository.findByIdOrNull(id)
        } catch (e: Exception) {
            throw Exception("Error getting user by name: ${e.message}")
        }
    }
}
