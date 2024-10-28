package api.vpopooapi.repository

import api.vpopooapi.model.StudentModel
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface StudentRepository : JpaRepository<StudentModel, Int> {

    @Query("SELECT s FROM StudentModel s " +
            "WHERE (:name = '' OR s.name = :name) " +
            "AND (:lastName = '' OR s.lastName = :lastName) " +
            "AND (:firstName = '' OR s.firstName = :firstName) " +
            "AND (:middleName = '' OR s.middleName = :middleName)")
    fun findStudentByName(
        @Param("name") name: String?,
        @Param("lastName") lastName: String?,
        @Param("firstName") firstName: String?,
        @Param("middleName") middleName: String?
    ): List<StudentModel>


    @Query("SELECT s FROM StudentModel s WHERE s.isDeleted = false")
    fun findAllByLogic(pageable: Pageable): Page<StudentModel?>

    @Modifying
    @Transactional
    @Query("DELETE FROM StudentModel s WHERE s.id IN :studentIds")
    fun deleteMultipleStudents(@Param("studentIds") studentIds: List<Int>)
}
