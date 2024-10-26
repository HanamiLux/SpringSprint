package com.example.vpopoo.controllers

import com.example.vpopoo.model.Subject
import com.example.vpopoo.service.SubjectApiService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
class SubjectController(private val subjectApiService: SubjectApiService) {

    @GetMapping("/subjects")
    fun getAllSubjects(
        model: Model,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): String {
        val subjects = subjectApiService.getAllSubjects(page, size)
        val allSubjects = subjectApiService.getAllSubjectsList()
        model.addAttribute("subjects", subjects)
        model.addAttribute("allSubjects", allSubjects)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", (subjects.size + size - 1) / size)
        model.addAttribute("pageSize", size)
        model.addAttribute("subject", Subject())
        return "subjectList"
    }

    @PostMapping("/subjects/addOrUpdate")
    fun addOrUpdateSubject(
        @Valid @ModelAttribute newSubject: Subject,
        bindingResult: BindingResult,
        model: Model
    ): String {
        if (bindingResult.hasErrors()) {
            val subjects = subjectApiService.getAllSubjectsList()
            model.addAttribute("subjects", subjects)
            model.addAttribute("subject", newSubject)
            return "subjectList"
        }
        subjectApiService.addOrUpdateSubject(newSubject)
        return "redirect:/subjects"
    }

    @PostMapping("/subjects/delete")
    fun deleteSubject(@RequestParam id: Int, @RequestParam action: String): String {
        subjectApiService.deleteSubject(id, action)
        return "redirect:/subjects"
    }

    @PostMapping("/subjects/deleteMultiple")
    fun deleteMultipleSubjects(@RequestParam subjectIds: List<Int>?): String {
        if (!subjectIds.isNullOrEmpty()) {
            subjectApiService.deleteMultipleSubjects(subjectIds)
        }
        return "redirect:/subjects"
    }
}