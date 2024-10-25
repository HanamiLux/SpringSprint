package api.vpopooapi.model

import jakarta.persistence.*

@Entity
@Table(name = "subjects")
open class Subject @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    @ManyToMany(mappedBy = "subjects")
    var teachers: MutableList<TeacherModel> = mutableListOf(),

    var isDeleted: Boolean = false
)