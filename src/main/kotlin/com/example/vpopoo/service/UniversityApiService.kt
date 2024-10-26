package com.example.vpopoo.service

import com.example.vpopoo.model.University
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class UniversityApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/universities"

    fun getAllUniversities(page: Int, size: Int): List<University> {
        return apiClient.getAll(apiUrl, page, size, object : ParameterizedTypeReference<List<University>>() {})
    }

    fun getAllUniversitiesList(): List<University> {
        return apiClient.getAll("$apiUrl/all", 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<University>>() {})
    }

    fun addOrUpdateUniversity(university: University): University? {
        return apiClient.addOrUpdate(apiUrl, university, University::class.java)
    }

    fun deleteUniversity(id: Int, action: String) {
        apiClient.delete(apiUrl, id, action)
    }

    fun deleteMultipleUniversities(ids: List<Int>) {
        apiClient.deleteMultiple(apiUrl, ids)
    }
}