package com.example.vpopoo.service

import com.example.vpopoo.model.Course
import com.example.vpopoo.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CourseService @Autowired constructor(private val courseRepository: CourseRepository) {

    fun getAllCourses(pageable: Pageable): Page<Course> = courseRepository.findAllByLogic(pageable)

    fun findAllCoursesList(): List<Course> = courseRepository.findAll()

    fun getCourseById(id: Int): Course? = courseRepository.findByIdOrNull(id)

    fun addCourse(course: Course): Course = courseRepository.save(course)

    fun updateCourse(id: Int, course: Course): Course? {
        return if (courseRepository.existsById(id)) {
            courseRepository.save(course)
        } else {
            null
        }
    }

    fun deleteCourse(id: Int): Boolean {
        return if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun logicalDeleteCourse(id: Int) {
        val course = courseRepository.findById(id).orElseThrow()
        course.isDeleted = true
        courseRepository.save(course)
    }

    fun deleteMultipleCourses(courseIds: List<Int>) {
        courseRepository.deleteMultipleCourses(courseIds)
    }
}
