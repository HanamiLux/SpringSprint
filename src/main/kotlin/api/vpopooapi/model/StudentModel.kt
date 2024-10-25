package api.vpopooapi.model

import jakarta.persistence.*

@Entity
@Table(name = "students")
open class StudentModel @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    var lastName: String? = null,

    var firstName: String? = null,

    var middleName: String? = null,

    var isDeleted: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "university_id")
    var university: University? = null,

    @OneToOne(mappedBy = "student", cascade = [CascadeType.MERGE])
    var gradeField: GradeModel? = null,

    @ManyToOne
    @JoinColumn(name = "course_id")
    var course: Course? = null
)