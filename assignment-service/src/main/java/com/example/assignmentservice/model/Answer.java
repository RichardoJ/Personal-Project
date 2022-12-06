package com.example.assignmentservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int student_id_answer;
    private int assignment_id_answer;
    private int assignment_status;
    private Float assignment_score;
    private String Blob_link;

    public Answer(Integer student_id, Integer assignment_id, Integer status, Float score, String url){
        this.student_id_answer = student_id;
        this.assignment_id_answer = assignment_id;
        this.assignment_status = status;
        this.assignment_score = score;
        this.Blob_link = url;
    }
    
}
