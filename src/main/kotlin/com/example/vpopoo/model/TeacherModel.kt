package com.example.vpopoo.model

data class TeacherModel(
    var id: Int? = null,
    var name: String? = null,
    var lastName: String? = null,
    var isDeleted: Boolean = false,
    var subject: String? = null,
    var age: Int? = null,
    var stage: Int? = null,
    var university: University? = null,
    var subjects: List<Subject> = listOf(),
    var subjectIds: List<Int> = listOf()
)