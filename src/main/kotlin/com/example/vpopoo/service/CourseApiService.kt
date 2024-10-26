package com.example.vpopoo.service

import com.example.vpopoo.model.Course
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class CourseApiService(private val apiClient: ApiClient) {

    private val apiUrl = "http://localhost:8081/api/courses"

    fun getAllCourses(page: Int, size: Int): List<Course> {
        return apiClient.getAll(apiUrl, page, size, object : ParameterizedTypeReference<List<Course>>() {})
    }

    fun getAllCoursesList(): List<Course> {
        return apiClient.getAll("$apiUrl/all", 0, Int.MAX_VALUE, object : ParameterizedTypeReference<List<Course>>() {})
    }

    fun addOrUpdateCourse(course: Course): Course? {
        return apiClient.addOrUpdate(apiUrl, course, Course::class.java)
    }

    fun deleteCourse(id: Int, action: String) {
        apiClient.delete(apiUrl, id, action)
    }

    fun deleteMultipleCourses(ids: List<Int>) {
        apiClient.deleteMultiple(apiUrl, ids)
    }
}
