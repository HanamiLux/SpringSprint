package api.vpopooapi.service

import api.vpopooapi.model.StudentModel
import api.vpopooapi.repository.StudentRepository
import api.vpopooapi.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class StudentServiceImpl @Autowired constructor(private val studentRepository: StudentRepository) : StudentService {
    override fun findAllStudent(): List<StudentModel?> {
        return studentRepository.findAll()
    }

    override fun findStudentById(id: Int): StudentModel? {
        return studentRepository.findById(id).orElseThrow()
    }

    override fun addStudent(student: StudentModel): StudentModel? {
        
        return studentRepository.save(student)
    }

    override fun deleteStudent(id: Int) {
        studentRepository.deleteById(id)
    }

    override fun findStudentByName(name: String?, lastName: String?, firstName: String?, middleName: String?): List<StudentModel> {
        return studentRepository.findStudentByName(name, lastName, firstName, middleName)
    }

    override fun deleteMultipleStudents(studentIds: List<Int>) {
        studentRepository.deleteMultipleStudents(studentIds)
    }

    override fun logicalDeleteStudent(id: Int) {
        val student = studentRepository.findById(id).orElseThrow()
        student.isDeleted = true
        studentRepository.save(student)
    }

    override fun findPaginatedStudents(pageable: Pageable): Page<StudentModel?> {
        return studentRepository.findAll(pageable)
    }
}
