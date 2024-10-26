package com.example.vpopoo.controllers

import com.example.vpopoo.model.Course
import com.example.vpopoo.service.CourseApiService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CourseController(private val courseApiService: CourseApiService) {

    @GetMapping("/courses")
    fun getAllCourses(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): String {
        val courses = courseApiService.getAllCourses(page, size)
        val allCourses = courseApiService.getAllCoursesList()
        model.addAttribute("courses", courses)
        model.addAttribute("allCourses", allCourses)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", (courses.size + size - 1) / size) // Пример вычисления общего числа страниц
        model.addAttribute("pageSize", size)
        model.addAttribute("course", Course())
        return "courseList"
    }

    @PostMapping("/courses/addOrUpdate")
    fun addOrUpdateCourse(
        @Valid @ModelAttribute newCourse: Course,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val allCourses = courseApiService.getAllCourses(0, Int.MAX_VALUE)
            model.addAttribute("courses", allCourses)
            model.addAttribute("course", newCourse)
            return "courseList"
        }
        courseApiService.addOrUpdateCourse(newCourse)
        return "redirect:/courses"
    }

    @PostMapping("/courses/delete")
    fun deleteCourse(@RequestParam id: Int, @RequestParam action: String): String {
        courseApiService.deleteCourse(id, action)
        return "redirect:/courses"
    }

    @PostMapping("/courses/deleteMultiple")
    fun deleteMultipleCourses(@RequestParam courseIds: List<Int>?): String {
        if (!courseIds.isNullOrEmpty()) {
            courseApiService.deleteMultipleCourses(courseIds)
        }
        return "redirect:/courses"
    }
}
