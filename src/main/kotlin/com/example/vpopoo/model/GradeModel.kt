package com.example.vpopoo.model

data class GradeModel(
    var id: Int? = null,
    var gradeContent: String? = null,
    var student: StudentModel? = null,
    var isDeleted: Boolean = false
)
