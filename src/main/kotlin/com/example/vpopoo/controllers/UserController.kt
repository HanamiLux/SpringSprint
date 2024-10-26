package com.example.vpopoo.controllers

import com.example.vpopoo.model.UserModel
import com.example.vpopoo.service.UserApiService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (private val userApiService: UserApiService) {
    @GetMapping
    fun getAllUsers(): List<UserModel> = userApiService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserModel> {
        val user = userApiService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/name")
    fun getUserByName(@RequestParam username: String): ResponseEntity<UserModel> {
        val user = userApiService.getUserByName(username)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/addOrUpdate")
    fun addOrUpdateCourse(
        @Valid @ModelAttribute newUser: UserModel,
        bindingResult: BindingResult,
        model: Model
    ): UserModel {
        return userApiService.addOrUpdateUser(newUser) ?: UserModel()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userApiService.deleteUser(id)
    }
}