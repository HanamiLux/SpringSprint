package api.vpopooapi.controllers

import api.vpopooapi.model.University
import api.vpopooapi.model.UniversityDTO
import api.vpopooapi.service.UniversityService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/universities")
class UniversityController(private val universityService: UniversityService) {

    @GetMapping
    fun getAllUniversities(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<UniversityDTO>> {
        val pageable = PageRequest.of(page, size)
        val universities = universityService.findPaginatedUniversities(pageable).content
        return ResponseEntity.ok(universities)
    }

    @GetMapping("/all")
    fun getAllUniversitiesList(): ResponseEntity<List<UniversityDTO>> {
        val x = ResponseEntity.ok(universityService.findAllUniversities())
        return x
    }

    @PostMapping
    fun addOrUpdateUniversity(@RequestBody newUniversity: University): ResponseEntity<University> {
        val updatedUniversity = universityService.addUniversity(newUniversity)
        return ResponseEntity.ok(updatedUniversity)
    }

    @DeleteMapping("/{id}")
    fun deleteUniversity(@PathVariable id: Int, @RequestParam action: String): ResponseEntity<Void> {
        when (action) {
            "logical" -> universityService.logicalDeleteUniversity(id)
            "physical" -> universityService.deleteUniversity(id)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/deleteMultiple")
    fun deleteMultipleUniversities(@RequestBody universityIds: List<Int>): ResponseEntity<Void> {
        if (universityIds.isNotEmpty()) {
            universityService.deleteMultipleUniversities(universityIds)
        }
        return ResponseEntity.noContent().build()
    }
}