package api.vpopooapi.service

import api.vpopooapi.model.University
import api.vpopooapi.model.UniversityDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UniversityService {
    fun findAllUniversities(): List<UniversityDTO>
    fun findUniversityById(id: Int): University?
    fun findPaginatedUniversities(pageable: Pageable): Page<UniversityDTO>
    fun findUniversityByName(name: String): List<University>
    fun addUniversity(university: University): University?
    fun deleteUniversity(id: Int)
    fun deleteMultipleUniversities(universityIds: List<Int>)
    fun logicalDeleteUniversity(id: Int)
}
