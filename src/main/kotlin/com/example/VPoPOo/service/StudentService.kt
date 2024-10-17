package com.example.VPoPOo.service

import com.example.VPoPOo.model.StudentModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface StudentService {
    fun findAllStudent(): List<StudentModel?>?
    fun findStudentById(id: Int): StudentModel?
    fun findPaginatedStudents(pageable: Pageable): Page<StudentModel?>
    fun findStudentByName(name: String?, lastName: String?, firstName: String?, middleName: String?): List<StudentModel?>
    fun addStudent(student: StudentModel): StudentModel?
    fun updateStudent(student: StudentModel): StudentModel?
    fun deleteStudent(id: Int)
    fun deleteMultipleStudents(studentIds: List<Int>)
    fun logicalDeleteStudent(id: Int)
}
