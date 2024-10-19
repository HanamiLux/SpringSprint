package com.example.vpopoo.repository

import com.example.vpopoo.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserModel, Long> {
    fun findByUsername(username: String?): UserModel?
}