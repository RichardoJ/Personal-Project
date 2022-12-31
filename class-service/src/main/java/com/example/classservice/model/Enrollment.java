package com.example.classservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollment")
public class Enrollment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_id", nullable = false)
    private int student_id_enrollment;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course_enrollment;

    public Enrollment(Integer student_id, Course empty){
        this.student_id_enrollment = student_id;
        this.course_enrollment = empty;
    }
}
