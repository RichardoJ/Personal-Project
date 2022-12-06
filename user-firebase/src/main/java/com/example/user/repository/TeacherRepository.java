package com.example.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.user.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{
    @Query(value = "Select * FROM teacher where teacher_email = :email", nativeQuery = true)
    Optional<Teacher> findByEmail(@Param("email") String email);
}
