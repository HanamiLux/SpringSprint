package api.vpopooapi.service

import api.vpopooapi.model.StudentDTO
import api.vpopooapi.model.StudentModel
import api.vpopooapi.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl @Autowired constructor(
    private val studentRepository: StudentRepository
) : StudentService {

    override fun findAllStudents(): List<StudentDTO> {
        return studentRepository.findAll().map { it.toDTO() }
    }

    override fun findStudentById(id: Int): StudentModel {
        val student = studentRepository.findById(id).orElseThrow()
        return student
    }

    override fun addStudent(student: StudentModel) {
        studentRepository.save(student)
    }

    override fun deleteStudent(id: Int) {
        studentRepository.deleteById(id)
    }

    override fun findStudentByName(name: String?, lastName: String?, firstName: String?, middleName: String?): List<StudentModel> {
        return studentRepository.findStudentByName(name, lastName, firstName, middleName).map { it }
    }

    override fun deleteMultipleStudents(studentIds: List<Int>) {
        studentRepository.deleteMultipleStudents(studentIds)
    }

    override fun logicalDeleteStudent(id: Int) {
        val student = studentRepository.findById(id).orElseThrow()
        student.isDeleted = true
        studentRepository.save(student)
    }

    override fun findPaginatedStudents(pageable: Pageable): Page<StudentDTO> {
        val students = studentRepository.findAllByLogic(pageable)
        return students.map { it?.toDTO() }
    }

    // Преобразование модели в DTO
    private fun StudentModel.toDTO(): StudentDTO {
        return StudentDTO(
            id = this.id!!,
            name = this.name!!,
            lastName = this.lastName!!,
            isDeleted = this.isDeleted,
            firstName = this.firstName!!,
            middleName = this.middleName!!,
            university = this.university?.name ?: "",
            gradeField = this.gradeField?.gradeContent ?: "",
            course = this.course?.name ?: ""
        )
    }
}

