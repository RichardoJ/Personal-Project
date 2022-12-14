package com.example.classservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.classservice.model.Course;


public interface CourseRepository extends JpaRepository<Course, Integer>{
    
    // @Query(value = "SELECT c.id, c.course_name, m.modules_name FROM course c JOIN modules m ON c.id = m.course_module_id where c.id = :course_id", nativeQuery = true)
    // List<Module> findAllModules(@Param("course_id") Integer id);
}
