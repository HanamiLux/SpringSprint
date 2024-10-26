package api.vpopooapi.controllers

import api.vpopooapi.model.Course
import api.vpopooapi.service.CourseService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(private val courseService: CourseService) {

    @GetMapping
    fun getAllCourses(@RequestParam(defaultValue = "0") page: Int,
                      @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<Course>> {
        val pageable = PageRequest.of(page, size)
        val courses = courseService.getAllCourses(pageable).content
        return ResponseEntity.ok(courses)
    }

    @GetMapping("/all")
    fun getAllCoursesList(): ResponseEntity<List<Course>> {
        return ResponseEntity.ok(courseService.findAllCoursesList())
    }

    @PostMapping
    fun addOrUpdateCourse(@RequestBody course: Course): ResponseEntity<Course> {
        val updatedCourse = courseService.addCourse(course)
        return ResponseEntity.ok(updatedCourse)
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
