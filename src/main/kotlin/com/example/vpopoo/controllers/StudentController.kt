package com.example.vpopoo.controllers

import com.example.vpopoo.model.StudentModel
import com.example.vpopoo.service.CourseApiService
import com.example.vpopoo.service.GradeApiService
import com.example.vpopoo.service.StudentApiService
import com.example.vpopoo.service.UniversityApiService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class StudentController(
    private val courseService: CourseApiService,
    private val studentService: StudentApiService,
    private val universityService: UniversityApiService,
    private val gradeService: GradeApiService
) {

    @GetMapping("/students")
    fun getAllStudents(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): String {
        val students = studentService.getAllStudents(page, size)
        val allStudents = studentService.getAllStudentsList()
        val availableUniversities = universityService.getAllUniversitiesList()
        val availableGrades = gradeService.getAllGradesList()
        val availableCourses = courseService.getAllCoursesList()

        model.addAttribute("students", students)
        model.addAttribute("allStudents", allStudents)
        model.addAttribute("availableUniversities", availableUniversities)
        model.addAttribute("availableGrades", availableGrades)
        model.addAttribute("availableCourses", availableCourses)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", (allStudents.size + size - 1) / size)
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
        val students = studentService.getAllStudentsList()

        // Фильтрация по университету и оценке
        val filteredStudents = students.filter { student ->
            (university.isNullOrEmpty() || student.university?.name == university) &&
                    (grade.isNullOrEmpty() || student.gradeField?.gradeContent == grade)
        }

        val availableUniversities = universityService.getAllUniversitiesList()
        val availableGrades = gradeService.getAllGradesList()
        val availableCourses = courseService.getAllCoursesList()

        model.addAttribute("students", filteredStudents)
        model.addAttribute("availableUniversities", availableUniversities)
        model.addAttribute("availableGrades", availableGrades)
        model.addAttribute("availableCourses", availableCourses)
        model.addAttribute("student", StudentModel())

        return "studentList"
    }

    @PostMapping("/students/addOrUpdate")
    fun addOrUpdateStudent(
        @Valid @ModelAttribute newStudent: StudentModel,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val students = studentService.getAllStudentsList()
            val availableUniversities = universityService.getAllUniversitiesList()
            val availableGrades = gradeService.getAllGradesList()
            val availableCourses = courseService.getAllCoursesList()

            model.addAttribute("students", students)
            model.addAttribute("availableUniversities", availableUniversities)
            model.addAttribute("availableGrades", availableGrades)
            model.addAttribute("availableCourses", availableCourses)
            model.addAttribute("student", newStudent)

            return "studentList"
        }

        val grade = gradeService.getGradeById(newStudent.gradeField?.id ?: 0)
        if (grade != null) {
            newStudent.gradeField = grade
            grade.student = newStudent
        }

        studentService.addOrUpdateStudent(newStudent)
        return "redirect:/students"
    }

    @PostMapping("/students/delete")
    fun deleteStudent(@RequestParam id: Int, @RequestParam action: String): String {
        val student = studentService.getStudentById(id)
        student?.gradeField?.student = null
        gradeService.addOrUpdateGrade(student?.gradeField ?: return "redirect:/students")
        studentService.deleteStudent(id, action)
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
        val students = studentService.getStudentsByName(name, lastName, firstName, middleName)
        model.addAttribute("students", students)
        model.addAttribute("student", StudentModel())
        return "studentList"
    }

    @PostMapping("/students/deleteMultiple")
    fun deleteMultipleStudents(@RequestParam studentIds: List<Int>?): String {
        if (studentIds.isNullOrEmpty()) return "redirect:/students"

        studentIds.forEach { studentId ->
            val student = studentService.getStudentById(studentId)
            student?.gradeField?.student = null
            gradeService.addOrUpdateGrade(student?.gradeField ?: return "redirect:/students")
        }

        studentService.deleteMultipleStudents(studentIds)
        return "redirect:/students"
    }
}