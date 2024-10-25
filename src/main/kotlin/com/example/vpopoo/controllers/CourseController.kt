package com.example.vpopoo.controllers

import com.example.vpopoo.model.Course
import com.example.vpopoo.service.CourseService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CourseController(private val courseService: CourseService) {

    @GetMapping("/courses")
    fun getAllCourses(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): String {
        val pageable = PageRequest.of(page, size)
        val coursesPage = courseService.getAllCourses(pageable)
        val allCourses = courseService.findAllCoursesList()
        val courses = coursesPage.content
        model.addAttribute("courses", courses)
        model.addAttribute("allCourses", allCourses)
        model.addAttribute("currentPage", coursesPage.number)
        model.addAttribute("totalPages", coursesPage.totalPages)
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
            val allCourses = courseService.findAllCoursesList()
            model.addAttribute("courses", allCourses)
            model.addAttribute("course", newCourse)
            return "courseList"
        }
        courseService.addCourse(newCourse)
        return "redirect:/courses"
    }

    @PostMapping("/courses/delete")
    fun deleteCourse(@RequestParam id: Int, @RequestParam action: String): String {
        when (action) {
            "logical" -> courseService.logicalDeleteCourse(id)
            "physical" -> courseService.deleteCourse(id)
        }
        return "redirect:/courses"
    }

    @PostMapping("/courses/deleteMultiple")
    fun deleteMultipleCourses(@RequestParam courseIds: List<Int>?): String {
        if (courseIds.isNullOrEmpty()) return "redirect:/courses"
        courseService.deleteMultipleCourses(courseIds)
        return "redirect:/courses"
    }
}
