package api.vpopooapi.service

import api.vpopooapi.model.Subject
import api.vpopooapi.model.TeacherDTO
import api.vpopooapi.model.TeacherModel
import api.vpopooapi.repository.SubjectRepository
import api.vpopooapi.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class TeacherServiceImpl @Autowired constructor(private val teacherRepository: TeacherRepository, private val subjectRepository: SubjectRepository) :
    TeacherService {
    override fun findAllTeachers(): List<TeacherDTO> {
        val teachers = teacherRepository.findAll()
        return teachers.map { teacher ->
            TeacherDTO(
                id = teacher.id!!,
                name = teacher.name!!,
                lastName = teacher.lastName!!,
                subjectIds = teacher.subjects.mapNotNull { it.id },
                age = teacher.age!!,
                stage = teacher.stage!!,
                isDeleted = teacher.isDeleted
            )
        }
    }

    override fun findTeacherById(id: Int): TeacherModel? {
        return teacherRepository.findById(id).orElseThrow()
    }

    override fun addTeacher(teacher: TeacherModel): TeacherModel? {
        val subjects = teacher.subjects.mapNotNull {
            it.id?.let { subjectId ->
                subjectRepository.findById(subjectId).orElseThrow {
                    RuntimeException("Subject with ID $subjectId not found")
                }
            }
        }
        teacher.subjects = subjects.toMutableList()
        return teacherRepository.save(teacher)
    }

    override fun deleteTeacher(id: Int) {
        teacherRepository.deleteById(id)
    }

    override fun findTeacherByName(name: String?, lastName: String?): List<TeacherModel?> {
        return teacherRepository.findTeacherByName(name, lastName)
    }

    override fun deleteMultipleTeachers(teacherIds: List<Int>) {
        teacherRepository.deleteMultipleTeachers(teacherIds)
    }

    override fun logicalDeleteTeacher(id: Int) {
        val teacher = teacherRepository.findById(id).orElseThrow()
        teacher.isDeleted = true
        teacherRepository.save(teacher)
    }

    override fun findPaginatedTeachers(pageable: Pageable): Page<TeacherDTO> {
        val teachers = teacherRepository.findAllByLogic(pageable)
        return teachers.map { teacher ->
            TeacherDTO(
                id = teacher!!.id!!,
                name = teacher.name!!,
                lastName = teacher.lastName!!,
                subjectIds = teacher.subjects.mapNotNull { it.id },
                age = teacher.age!!,
                stage = teacher.stage!!,
                isDeleted = teacher.isDeleted
            )
        }
    }
}
