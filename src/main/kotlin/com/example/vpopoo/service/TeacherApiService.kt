package com.example.vpopoo.service

import com.example.vpopoo.model.TeacherModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class TeacherApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/teachers"

    fun getAllTeachers(page: Int, size: Int): List<TeacherModel> {
        return apiClient.getAll(apiUrl, page, size, object : ParameterizedTypeReference<List<TeacherModel>>() {})
    }

    fun getAllTeachersList(): List<TeacherModel> {
        return apiClient.getAll("$apiUrl/all", 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<TeacherModel>>() {})
    }

    fun addOrUpdateTeacher(teacher: TeacherModel): TeacherModel? {
        return apiClient.addOrUpdate(apiUrl, teacher, TeacherModel::class.java)
    }

    fun deleteTeacher(id: Int, action: String) {
        apiClient.delete(apiUrl, id, action)
    }

    fun deleteMultipleTeachers(ids: List<Int>) {
        apiClient.deleteMultiple(apiUrl, ids)
    }
}