package api.vpopooapi.controllers

import api.vpopooapi.model.StudentModel
import api.vpopooapi.service.StudentService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(private val studentService: StudentService) {

    @GetMapping("/find")
    fun findStudentsByName(
        @RequestParam name: String?,
        @RequestParam lastName: String?,
        @RequestParam firstName: String?,
        @RequestParam middleName: String?
    ): ResponseEntity<List<StudentModel>> {
        val students = studentService.findStudentByName(name, lastName, firstName, middleName)
        return ResponseEntity.ok(students)
    }

    @GetMapping
    fun getAllStudents(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<StudentModel?>> {
        val pageable = PageRequest.of(page, size)
        val students = studentService.findPaginatedStudents(pageable).content
        return ResponseEntity.ok(students)
    }

    @GetMapping("/all")
    fun getAllStudentsList(): ResponseEntity<List<StudentModel?>> {
        return ResponseEntity.ok(studentService.findAllStudents())
    }

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Int): ResponseEntity<StudentModel> {
        val student = studentService.findStudentById(id)
        return if (student != null) {
            ResponseEntity.ok(student)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun addOrUpdateStudent(@RequestBody student: StudentModel): ResponseEntity<StudentModel> {
        val updatedStudent = studentService.addStudent(student)
        return ResponseEntity.ok(updatedStudent)
    }

    @DeleteMapping("/{id}")
    fun deleteStudent(@PathVariable id: Int, @RequestParam action: String): ResponseEntity<Void> {
        when (action) {
            "logical" -> studentService.logicalDeleteStudent(id)
            "physical" -> studentService.deleteStudent(id)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/deleteMultiple")
    fun deleteMultipleStudents(@RequestBody studentIds: List<Int>): ResponseEntity<Void> {
        studentService.deleteMultipleStudents(studentIds)
        return ResponseEntity.noContent().build()
    }
}
