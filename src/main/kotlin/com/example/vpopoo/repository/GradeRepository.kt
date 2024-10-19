package com.example.vpopoo.repository

import com.example.vpopoo.model.Grade
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GradeRepository : JpaRepository<Grade, Int> {

    @Query("SELECT g FROM Grade g " +
            "WHERE (:grade IS NULL OR g.gradeContent = :grade)"
    )
    fun findGradeByGrade(
        @Param("grade") grade: String?
    ): List<Grade>

    @Modifying
    @Transactional
    @Query("DELETE FROM Grade g WHERE g.id IN :gradeIds")
    fun deleteMultipleGrades(@Param("gradeIds") gradeIds: List<Int>)
}