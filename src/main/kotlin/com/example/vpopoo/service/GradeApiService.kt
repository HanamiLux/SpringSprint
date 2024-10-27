package com.example.vpopoo.service

import com.example.vpopoo.model.GradeModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class GradeApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/grades"

    fun getAllGrades(page: Int, size: Int): List<GradeModel> {
        return apiClient.getAll(apiUrl, page, size, object : ParameterizedTypeReference<List<GradeModel>>() {})
    }

    fun getAllGradesList(): List<GradeModel> {
        return apiClient.getAll("$apiUrl/all", 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<GradeModel>>() {})
    }

    fun getGradeById(id: Int): GradeModel? {
        return apiClient.get("$apiUrl/$id", object : ParameterizedTypeReference<GradeModel>() {})
    }

    fun getGradeByName(name: String): GradeModel? {
        return apiClient.get("$apiUrl/$name", object : ParameterizedTypeReference<GradeModel>() {})
    }

    fun addOrUpdateGrade(grade: GradeModel): GradeModel? {
        return apiClient.addOrUpdate(apiUrl, grade, GradeModel::class.java)
    }

    fun deleteGrade(id: Int, action: String) {
        apiClient.delete(apiUrl, id, action)
    }

    fun deleteMultipleGrades(ids: List<Int>) {
        apiClient.deleteMultiple(apiUrl, ids)
    }

}
