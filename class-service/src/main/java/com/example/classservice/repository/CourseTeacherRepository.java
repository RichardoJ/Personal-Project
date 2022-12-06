package com.example.classservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.classservice.model.CourseTeacher;

public interface CourseTeacherRepository extends JpaRepository<CourseTeacher, Integer>{
    
}
