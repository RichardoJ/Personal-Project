package com.example.assignmentservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.assignmentservice.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer>{
    
    @Query(value = "SELECT * FROM assignment where course_id_assignment = :course_id", nativeQuery = true)
    List<Assignment> findByCourseId (@Param("course_id") Integer course_id);

    
}
