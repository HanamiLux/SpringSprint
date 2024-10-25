package api.vpopooapi.model

import jakarta.persistence.*

@Entity
data class Course(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String = "",

    var description: String = "",

    @OneToMany(mappedBy = "course", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var students: MutableList<StudentModel> = mutableListOf(),

    var isDeleted: Boolean = false

)