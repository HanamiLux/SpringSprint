package com.example.vpopoo.model

data class University(
    var id: Int? = null,
    var name: String? = null,
    var isDeleted: Boolean = false,
    var students: MutableList<StudentModel> = mutableListOf(),
    var teachers: MutableList<TeacherModel> = mutableListOf()
)