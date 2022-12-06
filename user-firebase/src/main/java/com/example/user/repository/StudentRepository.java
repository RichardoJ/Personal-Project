package com.example.user.repository;

import com.example.user.model.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query(value = "Select * FROM student where student_email = :email", nativeQuery = true)
    Optional<Student> findByEmail(@Param("email") String email);
}
