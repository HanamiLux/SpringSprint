package api.vpopooapi.controllers

import api.vpopooapi.model.GradeModel
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
    ): ResponseEntity<List<GradeModel>> {
        val pageable = PageRequest.of(page, size)
        val grades = gradeService.findAllGrades(pageable).content
        return ResponseEntity.ok(grades)
    }

    @GetMapping("/all")
    fun getAllGradesList(): ResponseEntity<List<GradeModel?>> {
        return ResponseEntity.ok(gradeService.findAllGradesList())
    }

    @PostMapping
    fun addOrUpdateGrade(@RequestBody newGrade: GradeModel): ResponseEntity<GradeModel> {
        val updatedGrade = gradeService.addGrade(newGrade)
        return ResponseEntity.ok(updatedGrade)
    }

    @GetMapping("/{id}")
    fun getGradeById(@PathVariable id: Int): ResponseEntity<GradeModel> {
        val gradeModel = gradeService.findGradeById(id)
        return if (gradeModel != null) {
            ResponseEntity.ok(gradeModel)
        } else {
            ResponseEntity.notFound().build()
        }
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
