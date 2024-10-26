package com.example.vpopoo.model

data class StudentModel(
    var id: Int? = null,
    var name: String? = null,
    var lastName: String? = null,
    var firstName: String? = null,
    var middleName: String? = null,
    var isDeleted: Boolean = false,
    var university: University? = null,
    var gradeField: GradeModel? = null,
    var course: Course? = null
)
