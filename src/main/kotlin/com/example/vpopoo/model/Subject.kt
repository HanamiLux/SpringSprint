package com.example.vpopoo.model

data class Subject(
    var id: Int? = null,
    var name: String? = null,
    var teachers: MutableList<TeacherModel> = mutableListOf(),
    var isDeleted: Boolean = false
)