package com.example.vpopoo.service

import com.example.vpopoo.model.Subject
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class SubjectApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/subjects"

    fun getAllSubjects(page: Int, size: Int): List<Subject> {
        return apiClient.getAll(apiUrl, page, size, object : ParameterizedTypeReference<List<Subject>>() {})
    }

    fun getAllSubjectsList(): List<Subject> {
        return apiClient.getAll("$apiUrl/all", 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<Subject>>() {})
    }

    fun getSubjectById(id: Int): Subject? {
        return apiClient.get("$apiUrl/$id", object : ParameterizedTypeReference<Subject>() {})
    }

    fun addOrUpdateSubject(subject: Subject): Subject? {
        return apiClient.addOrUpdate(apiUrl, subject, Subject::class.java)
    }

    fun deleteSubject(id: Int, action: String) {
        apiClient.delete(apiUrl, id, action)
    }

    fun deleteMultipleSubjects(ids: List<Int>) {
        apiClient.deleteMultiple(apiUrl, ids)
    }

    fun getSubjectsById(subjectIds: List<Int>): List<Subject>? {
        val idsString = subjectIds.joinToString(",")
        val url = "$apiUrl/byIds?ids=$idsString"
        return apiClient.gets(url, object : ParameterizedTypeReference<List<Subject>>() {})
    }
}