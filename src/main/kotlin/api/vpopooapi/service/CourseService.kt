package api.vpopooapi.service

import api.vpopooapi.model.Course
import api.vpopooapi.model.CourseDTO
import api.vpopooapi.repository.CourseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CourseServiceImpl @Autowired constructor(
    private val courseRepository: CourseRepository
) {

    fun findAllCourses(): List<CourseDTO> {
        val courses = courseRepository.findAll()
        return courses.map { it.toDTO()}
    }

    fun getCourseById(id: Int): Course {
        val course = courseRepository.findById(id).orElseThrow()
        return course
    }

    fun getCourseByName(name: String): Course {
        val course = courseRepository.findCourseByName(name).first()
        return course
    }

    fun findPaginatedCourses(pageable: Pageable): Page<CourseDTO> {
        val courses = courseRepository.findAllByLogic(pageable)
        return courses.map { it.toDTO() }
    }

     fun addCourse(course: Course) {
        courseRepository.save(course)
    }

    fun deleteCourse(id: Int): Boolean {
        return if (courseRepository.existsById(id)) {
            val g = getCourseById(id).students
            g.forEach{
                it.course = null
            }
            g.clear()
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
        for (id in courseIds) {
            val g = getCourseById(id).students
            g.forEach{
                it.course = null
            }
            g.clear()
        }
        courseRepository.deleteMultipleCourses(courseIds)
    }

    private fun Course.toDTO(): CourseDTO {
        return CourseDTO(
            id = this.id!!,
            name = this.name,
            description = this.description,
            isDeleted = this.isDeleted,
        )
    }
}
