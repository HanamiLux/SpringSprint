package com.example.vpopoo.model

data class Course(
    var id: Int? = null,
    var name: String = "",
    var description: String = "",
    var isDeleted: Boolean = false
)