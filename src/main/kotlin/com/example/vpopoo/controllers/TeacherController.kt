package com.example.vpopoo.controllers

import com.example.vpopoo.model.Subject
import com.example.vpopoo.model.TeacherModel
import com.example.vpopoo.service.SubjectApiService
import com.example.vpopoo.service.TeacherApiService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TeacherController(private val teacherApiService: TeacherApiService,
                        private val subjectApiService: SubjectApiService) {

    @GetMapping("/teachers")
    fun getAllTeachers(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): String {
        val teachers = teacherApiService.getAllTeachers(page, size)
        val subjects = subjectApiService.getAllSubjectsList()
        val allTeachers = teacherApiService.getAllTeachersList()
        model.addAttribute("teachers", teachers)
        model.addAttribute("allTeachers", allTeachers)
        model.addAttribute("availableSubjects", subjects)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", (teachers.size + size - 1) / size)
        model.addAttribute("pageSize", size)
        model.addAttribute("teacher", TeacherModel())
        return "teacherList"
    }

    @PostMapping("/teachers/addOrUpdate")
    fun addOrUpdateTeacher(
        @Valid @ModelAttribute newTeacher: TeacherModel,
        bindingResult: BindingResult,
        model: Model,
        @RequestParam(value = "subjects", required = false) subjectIds: String
    ): String {
           val selectedSubjects: List<Subject>? = subjectApiService
               .getSubjectsById(subjectIds.split(",").map { it.trim().toInt() })
            newTeacher.subjects = selectedSubjects!!.toMutableList()

        teacherApiService.addOrUpdateTeacher(newTeacher)
        return "redirect:/teachers"
    }

    @PostMapping("/teachers/delete")
    fun deleteTeacher(@RequestParam id: Int, @RequestParam action: String): String {
        teacherApiService.deleteTeacher(id, action)
        return "redirect:/teachers"
    }

    @PostMapping("/teachers/deleteMultiple")
    fun deleteMultipleTeachers(@RequestParam teacherIds: List<Int>?): String {
        if (!teacherIds.isNullOrEmpty()) {
            teacherApiService.deleteMultipleTeachers(teacherIds)
        }
        return "redirect:/teachers"
    }
}
