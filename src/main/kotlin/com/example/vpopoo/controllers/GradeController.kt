package com.example.vpopoo.controllers

import com.example.vpopoo.model.Grade
import com.example.vpopoo.service.GradeService
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
class GradeController {
    @Autowired
    private val gradeService: GradeService? = null

    @GetMapping("/grades")
    fun getAllGrades(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): String {
        val pageable = PageRequest.of(page, size)
        val gradesPage = gradeService?.findPaginatedGrades(pageable)

        val grades = gradesPage?.content
        model.addAttribute("grades", grades)
        model.addAttribute("currentPage", gradesPage?.number)
        model.addAttribute("totalPages", gradesPage?.totalPages)
        model.addAttribute("pageSize", size)
        model.addAttribute("grade", Grade())

        return "gradeList"
    }

    @PostMapping("/grades/addOrUpdate")
    fun addOrUpdateGrade(
        @Valid @ModelAttribute newGrade: Grade,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val grades = gradeService?.findAllGrades()
            model.addAttribute("grades", grades)
            model.addAttribute("grade", newGrade)
            return "gradeList"
        }
        gradeService?.addGrade(newGrade)
        return "redirect:/grades"
    }

    @PostMapping("/grades/delete")
    fun deleteGrade(@RequestParam id: Int, @RequestParam action: String): String {
        when (action) {
            "logical" -> {
                gradeService?.logicalDeleteGrade(id)
            }
            "physical" -> {
                gradeService?.deleteGrade(id)
            }
        }
        return "redirect:/grades"
    }

    @PostMapping("/grades/deleteMultiple")
    fun deleteMultipleGrades(@RequestParam gradeIds: List<Int>?): String {
        if (gradeIds.isNullOrEmpty()) return "redirect:/grades"
        gradeService?.deleteMultipleGrades(gradeIds)
        return "redirect:/grades"
    }
}