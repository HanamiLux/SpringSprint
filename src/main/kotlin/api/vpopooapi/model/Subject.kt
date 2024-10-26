package api.vpopooapi.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "subjects")
class Subject @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    @ManyToMany(mappedBy = "subjects")
    @JsonManagedReference
    var teachers: MutableList<TeacherModel> = mutableListOf(),

    var isDeleted: Boolean = false
)