package com.example.classservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.classservice.model.CourseTeacher;

public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Integer>{
    
    @Query(value = "SELECT * from course_teacher WHERE teacher_id_enrollment = :teacher_id", nativeQuery = true)
    List<CourseTeacher> findByTeacherId (@Param("teacher_id") Integer teacher_id_enrollment);

    @Query(value = "SELECT * from course_teacher WHERE course_id_enrollment = :course_id", nativeQuery = true)
    Optional<CourseTeacher> findByCourseId(@Param("course_id") Integer course_id_enrollment);
}
