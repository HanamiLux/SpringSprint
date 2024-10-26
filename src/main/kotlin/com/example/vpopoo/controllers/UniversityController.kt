package com.example.vpopoo.controllers

import com.example.vpopoo.model.University
import com.example.vpopoo.service.UniversityApiService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
class UniversityController(private val universityApiService: UniversityApiService) {

    @GetMapping("/universities")
    fun getAllUniversities(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): String {
        val universities = universityApiService.getAllUniversities(page, size)
        val allUniversities = universityApiService.getAllUniversitiesList()
        model.addAttribute("universities", universities)
        model.addAttribute("allUniversities", allUniversities)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", (universities.size + size - 1) / size)
        model.addAttribute("pageSize", size)
        model.addAttribute("university", University())
        return "universityList"
    }

    @PostMapping("/universities/addOrUpdate")
    fun addOrUpdateUniversity(
        @Valid @ModelAttribute newUniversity: University,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val universities = universityApiService.getAllUniversitiesList()
            model.addAttribute("universities", universities)
            model.addAttribute("university", newUniversity)
            return "universityList"
        }
        universityApiService.addOrUpdateUniversity(newUniversity)
        return "redirect:/universities"
    }

    @PostMapping("/universities/delete")
    fun deleteUniversity(@RequestParam id: Int, @RequestParam action: String): String {
        universityApiService.deleteUniversity(id, action)
        return "redirect:/universities"
    }

    @PostMapping("/universities/deleteMultiple")
    fun deleteMultipleUniversities(@RequestParam universityIds: List<Int>?): String {
        if (!universityIds.isNullOrEmpty()) {
            universityApiService.deleteMultipleUniversities(universityIds)
        }
        return "redirect:/universities"
    }
}