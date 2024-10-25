package com.example.vpopoo.controllers

import com.example.vpopoo.model.StudentModel
import com.example.vpopoo.service.CourseService
import com.example.vpopoo.service.GradeService
import com.example.vpopoo.service.StudentService
import com.example.vpopoo.service.UniversityService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class StudentController {
    @Autowired
    private lateinit var courseService: CourseService

    @Autowired
    private val studentService: StudentService? = null

    @Autowired
    private val universityService: UniversityService? = null

    @Autowired
    private val gradeService: GradeService? = null

    @GetMapping("/students")
    fun getAllStudents(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): String {
        val pageable = PageRequest.of(page, size)
        val studentsPage = studentService?.findPaginatedStudents(pageable)

        val students = studentsPage?.content
        val availableUniversities = universityService?.findAllUniversities()
        val availableGrades = gradeService?.findAllGradesList()
        val availableCourses = courseService.findAllCoursesList()
        model.addAttribute("availableUniversities", availableUniversities)
        model.addAttribute("availableGrades", availableGrades)
        model.addAttribute("availableCourses", availableCourses)
        model.addAttribute("students", students)
        model.addAttribute("currentPage", studentsPage?.number)
        model.addAttribute("totalPages", studentsPage?.totalPages)
        model.addAttribute("pageSize", size)
        model.addAttribute("student", StudentModel())

        return "studentList"
    }

    @GetMapping("/students/filter")
    fun filterStudents(
        @RequestParam(required = false) university: String?,
        @RequestParam(required = false) grade: String?,
        model: Model
    ): String {
        val students = studentService?.findAllStudent()

        // Фильтрация по университету и оценке
        val filteredStudents = students?.filter { student ->
            (university.isNullOrEmpty() || student?.university?.name == university) &&
                    (grade.isNullOrEmpty() || student?.gradeField?.gradeContent == grade)
        }

        val availableUniversities = universityService?.findAllUniversities()
        val availableGrades = gradeService?.findAllGradesList()
        val availableCourses = courseService.findAllCoursesList()
        model.addAttribute("students", filteredStudents)
        model.addAttribute("availableCourses", availableCourses)
        model.addAttribute("availableUniversities", availableUniversities)
        model.addAttribute("availableGrades", availableGrades)
        model.addAttribute("student", StudentModel()) // Добавьте эту строку

        return "studentList"
    }

    @PostMapping("/students/addOrUpdate")
    fun addOrUpdateStudent(
        @Valid @ModelAttribute newStudent: StudentModel,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val students = studentService?.findAllStudent()
            val availableUniversities = universityService?.findAllUniversities()
            val availableGrades = gradeService?.findAllGradesList()
            val availableCourses = courseService.findAllCoursesList()
            model.addAttribute("students", students)
            model.addAttribute("availableUniversities", availableUniversities)
            model.addAttribute("availableCourses", availableCourses)
            model.addAttribute("availableGrades", availableGrades)
            model.addAttribute("student", newStudent)
            return "studentList"
        }
        if(newStudent.id != null) {
            val existingStudent = studentService?.findStudentById(newStudent.id!!)
            existingStudent?.gradeField?.student = null
            val grade = gradeService?.findGradeById(newStudent.gradeField?.id!!)
            if (grade != null) {
                newStudent.gradeField = grade
                grade.student = newStudent
            }
        }
        else {
            val grade = gradeService?.findGradeById(newStudent.gradeField?.id!!)
            if (grade != null) {
                newStudent.gradeField = grade
                grade.student = newStudent
            }
        }
        studentService?.addStudent(newStudent)
        return "redirect:/students"
    }

    @PostMapping("/students/delete")
    fun deleteStudent(@RequestParam id: Int, @RequestParam action: String): String {
        val student = studentService?.findStudentById(id)
        student?.gradeField?.student = null
        gradeService?.addGrade(student?.gradeField!!)
        when (action) {
            "logical" -> {
                studentService?.logicalDeleteStudent(id)
            }

            "physical" -> {
                studentService?.deleteStudent(id)
            }
        }
        return "redirect:/students"
    }

    @GetMapping("/students/find")
    fun findStudentByName(
        model: Model,
        @RequestParam name: String?,
        @RequestParam lastName: String?,
        @RequestParam firstName: String?,
        @RequestParam middleName: String?
    ): String {
        model.addAttribute("students", studentService?.findStudentByName(name, lastName, firstName, middleName))
        model.addAttribute("student", StudentModel()) // Добавьте эту строку
        return "studentList"
    }

    @PostMapping("/students/deleteMultiple")
    fun deleteMultipleStudents(@RequestParam studentIds: List<Int>?): String {
        if (studentIds.isNullOrEmpty()) return "redirect:/students"
        for (studentId in studentIds) {
            val student = studentService?.findStudentById(studentId)
            student?.gradeField?.student = null
            gradeService?.addGrade(student?.gradeField!!)
        }
        studentService?.deleteMultipleStudents(studentIds)
        return "redirect:/students"
    }
}