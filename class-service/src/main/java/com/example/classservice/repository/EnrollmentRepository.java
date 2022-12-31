package com.example.classservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.classservice.model.Enrollment;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
    
    @Query(value = "SELECT * FROM enrollment WHERE student_id = :student_id", nativeQuery = true)
    List<Enrollment> findByStudentId(@Param("student_id") Integer student_id);

    @Modifying
    @Query(value = "DELETE FROM enrollment WHERE course_id = :course_id", nativeQuery = true)
    void deleteByCourseId(@Param("course_id") Integer course_id);

    @Modifying
    @Query(value = "DELETE FROM enrollment WHERE student_id = :student_id AND course_id = :course_id", nativeQuery = true)
    void deleteByStudentAndCourseId(@Param("student_id") Integer student_id, @Param("course_id") Integer course_id);
}
