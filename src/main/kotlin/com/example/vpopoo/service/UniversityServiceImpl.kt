package com.example.vpopoo.service

import com.example.vpopoo.model.University
import com.example.vpopoo.repository.UniversityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UniversityServiceImpl @Autowired constructor(private val universityRepository: UniversityRepository) : UniversityService {
    override fun findAllUniversities(): List<University?> {
        return universityRepository.findAll()
    }

    override fun findUniversityById(id: Int): University? {
        return universityRepository.findById(id).orElseThrow()
    }

    override fun findPaginatedUniversities(pageable: Pageable): Page<University?> {
        return universityRepository.findAll(pageable)
    }


    override fun findUniversityByName(name: String): List<University> {
        return universityRepository.findUniversityByName(name)
    }

    override fun addUniversity(university: University): University? {
        return universityRepository.save(university)
    }

    override fun deleteUniversity(id: Int) {
        universityRepository.deleteById(id)
    }

    override fun deleteMultipleUniversities(universityIds: List<Int>) {
        universityRepository.deleteMultipleUniversities(universityIds)
    }

    override fun logicalDeleteUniversity(id: Int) {
        val university = universityRepository.findById(id).orElseThrow()
        university.isDeleted = true
        universityRepository.save(university)
    }
}