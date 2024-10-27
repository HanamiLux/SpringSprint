package api.vpopooapi.service

import api.vpopooapi.model.GradeModel
import api.vpopooapi.model.GradeModelDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GradeService {
    fun findAllGrades(pageable: Pageable): Page<GradeModelDTO>
    fun findAllGradesList(): List<GradeModelDTO>
    fun findGradeById(id: Int): GradeModel
    fun findGradeByName(grade: String?): List<GradeModel>
    fun addGrade(newGrade: GradeModel)
    fun deleteGrade(id: Int)
    fun deleteMultipleGrades(gradeIds: List<Int>)
    fun logicalDeleteGrade(id: Int)
}
