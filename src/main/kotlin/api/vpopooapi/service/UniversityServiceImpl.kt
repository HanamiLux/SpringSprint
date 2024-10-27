package api.vpopooapi.service

import api.vpopooapi.model.University
import api.vpopooapi.model.UniversityDTO
import api.vpopooapi.repository.UniversityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class UniversityServiceImpl @Autowired constructor(private val universityRepository: UniversityRepository) :
    UniversityService {
    override fun findAllUniversities(): List<UniversityDTO> {
        val x = universityRepository.findAll()
        return x.map {
            UniversityDTO(
                id = it.id!!,
                name = it.name!!,
                isDeleted = it.isDeleted
            )
        }
    }

    override fun findUniversityById(id: Int): University {
        return universityRepository.findById(id).orElseThrow()
    }

    override fun findPaginatedUniversities(pageable: Pageable): Page<UniversityDTO> {
        val x = universityRepository.findAllByLogic(pageable)
        return x.map {
            UniversityDTO(
                id = it!!.id!!,
                name = it.name!!,
                isDeleted = it.isDeleted
            )
        }
    }


    override fun findUniversityByName(name: String): List<University> {
        return universityRepository.findUniversityByName(name)
    }

    override fun addUniversity(university: University): University? {
        return universityRepository.save(university)
    }

    override fun deleteUniversity(id: Int) {
        val g = findUniversityById(id).students
        g.forEach{
            it.university = null
        }
        g.clear()
        universityRepository.deleteById(id)
    }

    override fun deleteMultipleUniversities(universityIds: List<Int>) {
        for (id in universityIds) {
            val g = findUniversityById(id).students
            g.forEach{
                it.university = null
            }
            g.clear()
        }
        universityRepository.deleteMultipleUniversities(universityIds)
    }

    override fun logicalDeleteUniversity(id: Int) {
        val university = universityRepository.findById(id).orElseThrow()
        university.isDeleted = true
        universityRepository.save(university)
    }
}