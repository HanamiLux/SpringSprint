package api.vpopooapi.service

import api.vpopooapi.model.GradeModel
import api.vpopooapi.model.GradeModelDTO
import api.vpopooapi.repository.GradeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GradeServiceImpl @Autowired constructor(private val gradeRepository: GradeRepository) : GradeService {

    override fun findAllGrades(pageable: Pageable): Page<GradeModelDTO> {
        val grades = gradeRepository.findAllByLogic(pageable)
        return grades.map { it.toDTO() }
    }

    override fun findGradeById(id: Int): GradeModel {
        val grade = gradeRepository.findById(id).orElseThrow()
        return grade
    }

    override fun findAllGradesList(): List<GradeModelDTO> {
        return gradeRepository.findAll().map { it.toDTO() }
    }

    override fun findGradeByName(grade: String?): List<GradeModel> {
        return gradeRepository.findGradeByGrade(grade).map { it }
    }

    override fun addGrade(newGrade: GradeModel) {
        gradeRepository.save(newGrade)
    }

    override fun deleteGrade(id: Int) {
        gradeRepository.deleteById(id)
    }

    override fun deleteMultipleGrades(gradeIds: List<Int>) {
        gradeRepository.deleteMultipleGrades(gradeIds)
    }

    override fun logicalDeleteGrade(id: Int) {
        val grade = gradeRepository.findById(id).orElseThrow()
        grade.isDeleted = true
        gradeRepository.save(grade)
    }

    private fun GradeModel.toDTO(): GradeModelDTO {
        return GradeModelDTO(
            id = this.id!!,
            gradeContent = this.gradeContent!!,
            isDeleted = this.isDeleted
        )
    }
}
