package api.vpopooapi.service

import api.vpopooapi.model.TeacherDTO
import api.vpopooapi.model.TeacherModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TeacherService {
    fun findAllTeachers(): List<TeacherDTO>
    fun findTeacherById(id: Int): TeacherModel?
    fun findPaginatedTeachers(pageable: Pageable): Page<TeacherDTO>
    fun findTeacherByName(name: String?, lastName: String?): List<TeacherModel?>
    fun addTeacher(teacher: TeacherModel): TeacherModel?
    fun deleteTeacher(id: Int)
    fun deleteMultipleTeachers(teacherIds: List<Int>)
    fun logicalDeleteTeacher(id: Int)
}
