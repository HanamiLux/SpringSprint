package com.example.vpopoo.service

import com.example.vpopoo.model.Subject
import com.example.vpopoo.repository.SubjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SubjectServiceImpl @Autowired constructor(private val subjectRepository: SubjectRepository) : SubjectService {
    override fun findAllSubjects(): List<Subject?> {
        return subjectRepository.findAll()
    }

    override fun findSubjectById(id: Int): Subject? {
        return subjectRepository.findById(id).orElseThrow()
    }

    override fun findPaginatedSubjects(pageable: Pageable): Page<Subject?> {
        return subjectRepository.findAll(pageable)
    }

    override fun findSubjectByName(name: String?): List<Subject> {
        return subjectRepository.findSubjectByName(name)
    }

    override fun addSubject(subject: Subject): Subject? {
        return subjectRepository.save(subject)
    }

    override fun deleteSubject(id: Int) {
        subjectRepository.deleteById(id)
    }

    override fun deleteMultipleSubjects(subjectIds: List<Int>) {
        subjectRepository.deleteMultipleSubjects(subjectIds)
    }

    override fun logicalDeleteSubject(id: Int) {
        val subject = subjectRepository.findById(id).orElseThrow()
        subject.isDeleted = true
        subjectRepository.save(subject)
    }
}