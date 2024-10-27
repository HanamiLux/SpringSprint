package api.vpopooapi.controllers

import api.vpopooapi.model.Course
import api.vpopooapi.model.CourseDTO
import api.vpopooapi.service.CourseServiceImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(private val courseService: CourseServiceImpl) {

    @GetMapping
    fun getAllCourses(@RequestParam(defaultValue = "0") page: Int,
                      @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<CourseDTO>> {
        val pageable = PageRequest.of(page, size)
        val courses = courseService.findPaginatedCourses(pageable).content
        return ResponseEntity.ok(courses)
    }

    @GetMapping("/all")
    fun getAllCoursesList(): ResponseEntity<List<CourseDTO>> {
        return ResponseEntity.ok(courseService.findAllCourses())
    }

    @PostMapping
    fun addOrUpdateCourse(@RequestBody course: Course): ResponseEntity<Void> {
        val updatedCourse = courseService.addCourse(course)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: Int, @RequestParam action: String): ResponseEntity<Void> {
        when (action) {
            "logical" -> courseService.logicalDeleteCourse(id)
            "physical" -> courseService.deleteCourse(id)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/deleteMultiple")
    fun deleteMultipleCourses(@RequestBody courseIds: List<Int>): ResponseEntity<Void> {
        if (courseIds.isNotEmpty()) {
            courseService.deleteMultipleCourses(courseIds)
        }
        return ResponseEntity.noContent().build()
    }
}
