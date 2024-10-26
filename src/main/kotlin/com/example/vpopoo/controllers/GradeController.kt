package com.example.vpopoo.controllers

import com.example.vpopoo.model.GradeModel
import com.example.vpopoo.service.GradeApiService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
class GradeController(private val gradeApiService: GradeApiService) {

    @GetMapping("/grades")
    fun getAllGrades(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): String {
        val grades = gradeApiService.getAllGrades(page, size)
        val allGradeModels = gradeApiService.getAllGradesList()
        model.addAttribute("gradeModels", grades)
        model.addAttribute("allGradeModels", allGradeModels)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", (grades.size + size - 1) / size)
        model.addAttribute("pageSize", size)
        model.addAttribute("gradeModel", GradeModel())
        return "gradeList"
    }

    @PostMapping("/grades/addOrUpdate")
    fun addOrUpdateGrade(
        @Valid @ModelAttribute newGrade: GradeModel,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val gradeModels = gradeApiService.getAllGradesList()
            model.addAttribute("gradeModels", gradeModels)
            model.addAttribute("gradeModel", newGrade)
            return "gradeList"
        }
        gradeApiService.addOrUpdateGrade(newGrade)
        return "redirect:/grades"
    }

    @PostMapping("/grades/delete")
    fun deleteGrade(@RequestParam id: Int, @RequestParam action: String): String {
            gradeApiService.deleteGrade(id, action)
        return "redirect:/grades"
    }

    @PostMapping("/grades/deleteMultiple")
    fun deleteMultipleGrades(@RequestParam gradeIds: List<Int>?): String {
        if (!gradeIds.isNullOrEmpty()) {
            gradeApiService.deleteMultipleGrades(gradeIds)
        }
        return "redirect:/grades"
    }
}
