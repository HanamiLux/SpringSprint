package com.example.vpopoo.service

import com.example.vpopoo.model.University
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UniversityService {
    fun findAllUniversities(): List<University?>?
    fun findUniversityById(id: Int): University?
    fun findPaginatedUniversities(pageable: Pageable): Page<University?>
    fun findUniversityByName(name: String): List<University>
    fun addUniversity(university: University): University?
    fun deleteUniversity(id: Int)
    fun deleteMultipleUniversities(universityIds: List<Int>)
    fun logicalDeleteUniversity(id: Int)
}
