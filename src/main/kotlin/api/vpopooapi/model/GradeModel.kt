package api.vpopooapi.model

import jakarta.persistence.*

@Entity
@Table(name = "grades")
open class GradeModel @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var gradeContent: String? = null,

    @OneToOne
    @JoinColumn(name = "student_id")
    var student: StudentModel? = null,

    var isDeleted: Boolean = false
)