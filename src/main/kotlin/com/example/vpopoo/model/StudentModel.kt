package com.example.vpopoo.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "students")
open class StudentModel @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @NotBlank(message = "Name is mandatory")
    var name: String? = null,

    @NotBlank(message = "Last name is mandatory")
    var lastName: String? = null,

    @NotBlank(message = "First name is mandatory")
    var firstName: String? = null,

    var middleName: String? = null,

    var isDeleted: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "university_id")
    var university: University? = null,

    @OneToOne(mappedBy = "student", cascade = [CascadeType.ALL])
    var grade: GradeModel? = null
)