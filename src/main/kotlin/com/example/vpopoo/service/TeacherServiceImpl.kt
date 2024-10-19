package com.example.vpopoo.service

import com.example.vpopoo.model.TeacherModel
import com.example.vpopoo.repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


//Сервисный слой отвечает за бизнес-логику приложения. Он использует репозиторий для выполнения операций с данными и может включать дополнительные проверки или преобразования данных
//так же мы тут можем настроить инкапсуляцию
//А если простыми словами тут происходит разделенние запросов от контроллера к сервису
@Service
class TeacherServiceImpl @Autowired constructor(private val teacherRepository: TeacherRepository) : TeacherService {
    override fun findAllTeachers(): List<TeacherModel?> {
        return teacherRepository.findAll()
    }

    override fun findTeacherById(id: Int): TeacherModel? {
        return teacherRepository.findById(id).orElseThrow()
    }

    override fun addTeacher(teacher: TeacherModel): TeacherModel? {
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

    override fun findPaginatedTeachers(pageable: Pageable): Page<TeacherModel?> {
        return teacherRepository.findAll(pageable)
    }
}
