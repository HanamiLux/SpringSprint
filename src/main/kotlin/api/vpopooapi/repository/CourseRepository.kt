package api.vpopooapi.repository

import api.vpopooapi.model.Course
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Int> {

    @Query(
        "SELECT c FROM Course c " +
                "WHERE (:course IS NULL OR c.name = :course)"
    )
    fun findCourseByName(
        @Param("course") course: String?
    ): List<Course>

    @Query("SELECT c FROM Course c WHERE c.isDeleted = false")
    fun findAllByLogic(pageable: Pageable): Page<Course>

    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.id IN :courseIds")
    fun deleteMultipleCourses(@Param("courseIds") courseIds: List<Int>)
}