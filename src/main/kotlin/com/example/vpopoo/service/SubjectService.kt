package com.example.vpopoo.service

import com.example.vpopoo.model.Subject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SubjectService {
    fun findAllSubjects(): List<Subject?>?
    fun findSubjectById(id: Int): Subject?
    fun findPaginatedSubjects(pageable: Pageable): Page<Subject?>
    fun findSubjectByName(name: String?): List<Subject>
    fun addSubject(subject: Subject): Subject?
    fun deleteSubject(id: Int)
    fun deleteMultipleSubjects(subjectIds: List<Int>)
    fun logicalDeleteSubject(id: Int)
}