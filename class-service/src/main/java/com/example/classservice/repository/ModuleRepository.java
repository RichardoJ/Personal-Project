package com.example.classservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.classservice.model.Module;


public interface ModuleRepository extends JpaRepository<Module, Integer>{
    
    @Query(value = "Select * From modules m WHERE m.course_id = :course_id", nativeQuery = true)
    List<Module> findModulesByCourseId(@Param("course_id") Integer course_id);
}
