package api.vpopooapi.controllers

import api.vpopooapi.model.GradeModel
import api.vpopooapi.model.GradeModelDTO
import api.vpopooapi.service.GradeService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/grades")
class GradeController(private val gradeService: GradeService) {

    @GetMapping
    fun getAllGrades(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<MutableList<GradeModelDTO>> {
        val pageable = PageRequest.of(page, size)
        val grades = gradeService.findAllGrades(pageable).content
        return ResponseEntity.ok(grades)
    }

    @GetMapping("/all")
    fun getAllGradesList(): ResponseEntity<List<GradeModelDTO>> {
        val x = ResponseEntity.ok(gradeService.findAllGradesList())
        return x
    }

    @PostMapping
    fun addOrUpdateGrade(@RequestBody newGrade: GradeModel): ResponseEntity<Void> {
        gradeService.addGrade(newGrade)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteGrade(@PathVariable id: Int, @RequestParam action: String): ResponseEntity<Void> {
        when (action) {
            "logical" -> gradeService.logicalDeleteGrade(id)
            "physical" -> gradeService.deleteGrade(id)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/deleteMultiple")
    fun deleteMultipleGrades(@RequestBody gradeIds: List<Int>): ResponseEntity<Void> {
        if (gradeIds.isNotEmpty()) {
            gradeService.deleteMultipleGrades(gradeIds)
        }
        return ResponseEntity.noContent().build()
    }
}
