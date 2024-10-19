package com.example.vpopoo.service

import com.example.vpopoo.model.Grade
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GradeService {
    fun findAllGrades(): List<Grade?>?
    fun findGradeById(id: Int): Grade?
    fun findPaginatedGrades(pageable: Pageable): Page<Grade?>
    fun findGradeByName(grade: String?): List<Grade>
    fun addGrade(grade: Grade): Grade?
    fun deleteGrade(id: Int)
    fun deleteMultipleGrades(gradeIds: List<Int>)
    fun logicalDeleteGrade(id: Int)
}
