package com.example.vpopoo.repository

import com.example.vpopoo.model.Subject
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<Subject, Int> {

    @Query("SELECT s FROM Subject s " +
            "WHERE (:name IS NULL OR s.name = :name)")
    fun findSubjectByName(
        @Param("name") name: String?
    ): List<Subject>

    @Modifying
    @Transactional
    @Query("DELETE FROM Subject s WHERE s.id IN :subjectIds")
    fun deleteMultipleSubjects(@Param("subjectIds") subjectIds: List<Int>)
}