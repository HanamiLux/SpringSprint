package api.vpopooapi.model

data class TeacherDTO(
    val id: Int,
    val name: String,
    val lastName: String,
    val isDeleted: Boolean,
    val age: Int,
    val stage: Int,
    val subjectIds: List<Int>
)

data class StudentDTO(
    val id: Int,
    val name: String,
    val isDeleted: Boolean,
    val lastName: String,
    val firstName: String,
    val middleName: String,
    val university: String,
    val gradeField: String,
    val course: String
)

data class UniversityDTO(
    val id: Int,
    val name: String,
    val isDeleted: Boolean
)

data class GradeModelDTO(
    val id: Int,
    val gradeContent: String,
    val isDeleted: Boolean
)

data class CourseDTO(
    val id: Int,
    val name: String,
    val description: String,
    val isDeleted: Boolean
)
