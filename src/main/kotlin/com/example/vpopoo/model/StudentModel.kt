package com.example.vpopoo.model

data class StudentModel(
    var id: Int? = null,
    var name: String? = null,
    var lastName: String? = null,
    var firstName: String? = null,
    var middleName: String? = null,
    var isDeleted: Boolean = false,
    var university: String = "",
    var gradeField: String = "",
    var course: String = ""
)
