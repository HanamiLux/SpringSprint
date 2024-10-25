package api.vpopooapi.model

import jakarta.persistence.*

@Entity
@Table(name = "universities")
open class University @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    var isDeleted: Boolean = false,

    @OneToMany(mappedBy = "university", cascade = [CascadeType.ALL])
    var students: MutableList<StudentModel> = mutableListOf(),

    @OneToMany(mappedBy = "university", cascade = [CascadeType.ALL])
    var teachers: MutableList<TeacherModel> = mutableListOf()
)