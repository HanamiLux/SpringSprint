package com.example.vpopoo.controllers

import com.example.vpopoo.model.UserModel
import com.example.vpopoo.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private var port: Int = 8080

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userRepository: UserRepository

    private val baseUrl = "http://localhost:"

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @Test
    fun testGetAllUsers() {
        val user1 = UserModel(username = "user1", password = "password1")
        val user2 = UserModel(username = "user2", password = "password2")
        userRepository.save(user1)
        userRepository.save(user2)

        val response = restTemplate.getForEntity("$baseUrl$port/api/v1/users", List::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(2, response.body?.size)
    }

    @Test
    fun testGetUserById() {
        val user = UserModel(username = "user1", password = "password1")
        val savedUser = userRepository.save(user)

        val response = restTemplate.getForEntity("$baseUrl$port/api/v1/users/${savedUser.id}", UserModel::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(savedUser.id, response.body?.id)
        assertEquals(savedUser.username, response.body?.username)
    }

    @Test
    fun testCreateUser() {
        val user = UserModel(username = "user1", password = "password1")

        val response = restTemplate.postForEntity("$baseUrl$port/api/v1/users", user, UserModel::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertNotNull(response.body?.id)
        assertEquals(user.username, response.body?.username)
    }

    @Test
    fun testUpdateUser() {
        val user = UserModel(username = "user1", password = "password1")
        val savedUser = userRepository.save(user)

        val updatedUser = UserModel(username = "updatedUser", password = "updatedPassword")
        val requestEntity = HttpEntity(updatedUser)

        val response = restTemplate.exchange("$baseUrl$port/api/v1/users/${savedUser.id}", HttpMethod.PUT, requestEntity, UserModel::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(updatedUser.username, response.body?.username)
        assertEquals(updatedUser.password, response.body?.password)
    }

    @Test
    fun testDeleteUser() {
        val user = UserModel(username = "user1", password = "password1")
        val savedUser = userRepository.save(user)

        val response = restTemplate.exchange("$baseUrl$port/api/v1/users/${savedUser.id}", HttpMethod.DELETE, null, Void::class.java)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertEquals(0, userRepository.count())
    }
}