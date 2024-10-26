package com.example.vpopoo.model

data class Course(
    var id: Int? = null,
    var name: String = "",
    var description: String = "",
    var students: MutableList<StudentModel> = mutableListOf(),
    var isDeleted: Boolean = false
)