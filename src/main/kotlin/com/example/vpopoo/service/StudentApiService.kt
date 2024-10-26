package com.example.vpopoo.service

import com.example.vpopoo.model.StudentModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class StudentApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/students"

    fun getAllStudents(page: Int, size: Int): List<StudentModel> {
        return apiClient.getAll(apiUrl, page, size, object : ParameterizedTypeReference<List<StudentModel>>() {})
    }

    fun getAllStudentsList(): List<StudentModel> {
        return apiClient.getAll("$apiUrl/all", 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<StudentModel>>() {})
    }

    fun getStudentById(id: Int): StudentModel? {
        return apiClient.get("$apiUrl/$id", object : ParameterizedTypeReference<StudentModel>() {})
    }

    fun getStudentsByName(name: String?, lastName: String?, firstName: String?, middleName: String?): List<StudentModel>? {
        val url = "$apiUrl/find?name=$name&lastName=$lastName&firstName=$firstName&middleName=$middleName"
        return apiClient.get(url, object : ParameterizedTypeReference<List<StudentModel>>() {})
    }

    fun addOrUpdateStudent(student: StudentModel): StudentModel? {
        return apiClient.addOrUpdate(apiUrl, student, StudentModel::class.java)
    }

    fun deleteStudent(id: Int, action: String) {
        apiClient.delete(apiUrl, id, action)
    }

    fun deleteMultipleStudents(ids: List<Int>) {
        apiClient.deleteMultiple(apiUrl, ids)
    }
}