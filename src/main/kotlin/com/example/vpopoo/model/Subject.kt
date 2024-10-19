package com.example.vpopoo.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "subjects")
open class Subject @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @NotBlank(message = "Name is mandatory")
    var name: String? = null,

    @ManyToMany(mappedBy = "subjects")
    var teachers: MutableList<TeacherModel> = mutableListOf(),

    var isDeleted: Boolean = false
)