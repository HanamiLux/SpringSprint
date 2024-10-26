package com.example.vpopoo.service

import com.example.vpopoo.model.UserModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class UserApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/users"

    fun getAllUsers(): List<UserModel> {
        return apiClient.getAll(apiUrl, 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<UserModel>>() {})
    }

    fun getUserById(id: Long): UserModel? {
        return apiClient.get("$apiUrl/$id", object : ParameterizedTypeReference<UserModel>() {})
    }

    fun addOrUpdateUser(user: UserModel): UserModel? {
        return apiClient.addOrUpdate(apiUrl, user, UserModel::class.java)
    }

    fun deleteUser(id: Long) {
        apiClient.delete("$apiUrl/$id", id.toInt(), "")
    }

}
