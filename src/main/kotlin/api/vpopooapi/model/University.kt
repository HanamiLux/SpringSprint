package api.vpopooapi.model

import jakarta.persistence.*

@Entity
@Table(name = "universities")
class University @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    var isDeleted: Boolean = false,

    @OneToMany(mappedBy = "university", cascade = [CascadeType.ALL])
    var students: MutableList<StudentModel> = mutableListOf(),

    @OneToMany(mappedBy = "university", cascade = [CascadeType.ALL])
    var teachers: MutableList<TeacherModel> = mutableListOf()
){
    // Дополнительный конструктор для получения только name
    constructor(name: String?) : this(
        name = name,
        id = null, // или любое другое значение по умолчанию
        isDeleted = false, // значение по умолчанию для isDeleted
        students = mutableListOf(), // значение по умолчанию для students
        teachers = mutableListOf() // значение по умолчанию для teachers
    )
}

