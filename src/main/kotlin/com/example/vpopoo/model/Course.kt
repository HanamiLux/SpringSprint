package com.example.vpopoo.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Course(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @NotBlank(message = "Course name is required")
    var name: String = "",

    @NotBlank(message = "Course description is required")
    var description: String = "",

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var students: MutableList<StudentModel> = mutableListOf(),

    var isDeleted: Boolean = false

)