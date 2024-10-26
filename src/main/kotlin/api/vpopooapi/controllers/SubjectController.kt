package api.vpopooapi.controllers


import api.vpopooapi.model.StudentModel
import api.vpopooapi.model.Subject
import api.vpopooapi.service.SubjectService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subjects")
class SubjectController(private val subjectService: SubjectService) {

    @GetMapping
    fun getAllSubjects(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<Subject?>> {
        val pageable = PageRequest.of(page, size)
        val subjects = subjectService.findPaginatedSubjects(pageable).content
        return ResponseEntity.ok(subjects)
    }

    @GetMapping("/all")
    fun getAllSubjectsList(): ResponseEntity<List<Subject?>> {
        return ResponseEntity.ok(subjectService.findAllSubjects())
    }

    @GetMapping("/byIds")
    fun getSubjectsByIds(@RequestParam ids: List<Int>): ResponseEntity<List<Subject>> {
        val subjects = subjectService.findSubjectsByIds(ids)
        return ResponseEntity.ok(subjects)
    }

    @PostMapping
    fun addOrUpdateSubject(@RequestBody newSubject: Subject): ResponseEntity<Subject> {
        val updatedSubject = subjectService.addSubject(newSubject)
        return ResponseEntity.ok(updatedSubject)
    }

    @GetMapping("/{id}")
    fun getStudentById(@PathVariable id: Int): ResponseEntity<Subject> {
        val subject = subjectService.findSubjectById(id)
        return if (subject != null) {
            ResponseEntity.ok(subject)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSubject(@PathVariable id: Int, @RequestParam action: String): ResponseEntity<Void> {
        when (action) {
            "logical" -> subjectService.logicalDeleteSubject(id)
            "physical" -> subjectService.deleteSubject(id)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/deleteMultiple")
    fun deleteMultipleSubjects(@RequestBody subjectIds: List<Int>): ResponseEntity<Void> {
        if (subjectIds.isNotEmpty()) {
            subjectService.deleteMultipleSubjects(subjectIds)
        }
        return ResponseEntity.noContent().build()
    }
}