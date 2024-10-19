package com.example.vpopoo.repository

import com.example.vpopoo.model.University
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UniversityRepository : JpaRepository<University, Int> {

    @Query("SELECT u FROM University u " +
            "WHERE (:name IS NULL OR u.name = :name)")
    fun findUniversityByName(
        @Param("name") name: String?
    ): List<University>

    @Modifying
    @Transactional
    @Query("DELETE FROM University u WHERE u.id IN :universityIds")
    fun deleteMultipleUniversities(@Param("universityIds") universityIds: List<Int>)
}