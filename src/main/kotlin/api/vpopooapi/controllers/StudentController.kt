package api.vpopooapi.controllers

import api.vpopooapi.model.StudentDTO
import api.vpopooapi.model.StudentModel
import api.vpopooapi.service.CourseServiceImpl
import api.vpopooapi.service.GradeServiceImpl
import api.vpopooapi.service.StudentService
import api.vpopooapi.service.UniversityServiceImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/students")
class StudentController(
    private val studentService: StudentService,
    private val universityServiceImpl: UniversityServiceImpl,
    private val courseServiceImpl: CourseServiceImpl,
    private val gradeServiceImpl: GradeServiceImpl,
) {

    @GetMapping("/find")
    fun findStudentsByName(
        @RequestParam name: String?,
        @RequestParam lastName: String?,
        @RequestParam firstName: String?,
        @RequestParam middleName: String?
    ): ResponseEntity<List<StudentDTO>> {
        val students = studentService.findStudentByName(name, lastName, firstName, middleName)
        return ResponseEntity.ok(students)
    }

    @GetMapping
    fun getAllStudents(@RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "10") size: Int): ResponseEntity<List<StudentDTO>> {
        val pageable = PageRequest.of(page, size)
        val students = studentService.findPaginatedStudents(pageable).content
        return ResponseEntity.ok(students)
    }

    @GetMapping("/all")
    fun getAllStudentsList(): ResponseEntity<List<StudentDTO?>> {
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
    fun addOrUpdateStudent(@RequestBody studentDto: StudentDTO): ResponseEntity<Void> {
        val university = universityServiceImpl.findUniversityByName(studentDto.university).first()
        val gradeModel = gradeServiceImpl.findGradeByName(studentDto.gradeField).first()
        val course = courseServiceImpl.getCourseByName(studentDto.course)
        val student = StudentModel(
            id = studentDto.id,
            name = studentDto.name,
            isDeleted = studentDto.isDeleted,
            gradeField = gradeModel,
            firstName = studentDto.firstName,
            middleName = studentDto.middleName,
            lastName = studentDto.lastName,
            course = course,
            university = university
        )
        studentService.addStudent(student)
        return ResponseEntity.noContent().build()
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
