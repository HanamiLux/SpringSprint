package api.vpopooapi.repository

import api.vpopooapi.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserModel, Long> {
    fun findByUsername(username: String?): UserModel?
}