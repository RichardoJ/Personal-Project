package com.example.classservice.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String course_name;
    private Date start_date;
    private Date end_date;
    private String course_link;
    private String details;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    private List<Module> modules;

    @JsonIgnore
    @OneToMany(mappedBy = "course_enrollment")
    private List<Enrollment> enrollments;

    @JsonIgnore
    @OneToMany(mappedBy = "course_teacher")
    private List<CourseTeacher> courseTeachers;
}