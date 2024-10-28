package api.vpopooapi.service

import api.vpopooapi.model.StudentDTO
import api.vpopooapi.model.StudentModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface StudentService {
    fun findAllStudents(): List<StudentDTO?>
    fun findStudentById(id: Int): StudentModel?
    fun findPaginatedStudents(pageable: Pageable): Page<StudentDTO>
    fun findStudentByName(name: String?, lastName: String?, firstName: String?, middleName: String?): List<StudentDTO>
    fun addStudent(student: StudentModel)
    fun deleteStudent(id: Int)
    fun deleteMultipleStudents(studentIds: List<Int>)
    fun logicalDeleteStudent(id: Int)
}
