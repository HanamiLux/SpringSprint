package api.vpopooapi.controllers

import api.vpopooapi.model.TeacherDTO
import api.vpopooapi.model.TeacherModel
import api.vpopooapi.service.TeacherService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/teachers")
class TeacherController(private val teacherService: TeacherService) {

    @GetMapping
    fun getAllTeachers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<TeacherDTO?>> {
        val pageable = PageRequest.of(page, size)
        val teachers = teacherService.findPaginatedTeachers(pageable).content
        return ResponseEntity.ok(teachers)
    }

    @GetMapping("/all")
    fun getAllTeachersList(): ResponseEntity<List<TeacherDTO?>> {
        val x = teacherService.findAllTeachers()
        return ResponseEntity.ok(x)
    }

    @PostMapping
    fun addOrUpdateTeacher(@RequestBody teacher: TeacherModel): ResponseEntity<TeacherModel> {
        val updatedTeacher = teacherService.addTeacher(teacher)
        return ResponseEntity.ok(updatedTeacher)
    }

    @DeleteMapping("/{id}")
    fun deleteTeacher(@PathVariable id: Int, @RequestParam action: String): ResponseEntity<Void> {
        when (action) {
            "logical" -> teacherService.logicalDeleteTeacher(id)
            "physical" -> teacherService.deleteTeacher(id)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/deleteMultiple")
    fun deleteMultipleTeachers(@RequestBody teacherIds: List<Int>): ResponseEntity<Void> {
        if (teacherIds.isNotEmpty()) {
            teacherService.deleteMultipleTeachers(teacherIds)
        }
        return ResponseEntity.noContent().build()
    }
}