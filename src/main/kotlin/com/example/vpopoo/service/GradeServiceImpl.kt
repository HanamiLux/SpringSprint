package com.example.vpopoo.service

import com.example.vpopoo.model.Grade
import com.example.vpopoo.repository.GradeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GradeServiceImpl @Autowired constructor(private val gradeRepository: GradeRepository) : GradeService {
    override fun findAllGrades(): List<Grade?> {
        return gradeRepository.findAll()
    }

    override fun findGradeById(id: Int): Grade? {
        return gradeRepository.findById(id).orElseThrow()
    }

    override fun findPaginatedGrades(pageable: Pageable): Page<Grade?> {
        return gradeRepository.findAll(pageable)
    }

    override fun findGradeByName(grade: String?): List<Grade> {
        return gradeRepository.findGradeByGrade(grade)
    }

    override fun addGrade(grade: Grade): Grade? {
        return gradeRepository.save(grade)
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
}