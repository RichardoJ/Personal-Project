package com.example.assignmentservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.assignmentservice.model.Answer;
import com.example.assignmentservice.model.CourseAnswer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{

    @Query(value = "SELECT * FROM answers WHERE student_id_answer = :student_id && assignment_id_answer = :assignment_id", nativeQuery = true)
    Optional<Answer> findAnswerByStudentID (@Param("student_id") Integer student_id, @Param("assignment_id") Integer assignment_id);

    @Query(nativeQuery = true)
    Optional<List<CourseAnswer>> findCourseAnswer(@Param("student_id") Integer student_id, @Param("course_id") Integer course_id);

    // @Query(value = "SELECT * FROM answers JOIN assignment ON answers.assignment_id_answer = assignment.id WHERE assignment.id = :assignment_id", nativeQuery = true)
    // Optional<CustomModel> findStatusByAssignmentID (@Param("assignment_id") Integer assignment_id);
}
