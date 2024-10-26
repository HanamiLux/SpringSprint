package api.vpopooapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "teachers")
class TeacherModel @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    var lastName: String? = null,

    var isDeleted: Boolean = false,

    var subject: String? = null,

    var age: Int? = null,

    var stage: Int? = null,

    @ManyToOne
    @JoinColumn(name = "university_id")
    var university: University? = null,

    @ManyToMany
    @JoinTable(
        name = "teacher_subject",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "subject_id")]
    )
    @JsonBackReference
    var subjects: MutableList<Subject> = mutableListOf(),

    )