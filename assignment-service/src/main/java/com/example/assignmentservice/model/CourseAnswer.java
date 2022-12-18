package com.example.assignmentservice.model;

import lombok.Getter;

@Getter
public class CourseAnswer {
    private Float score;
    private Integer studentId;
    private Integer courseId;

    public CourseAnswer(Float score, Integer studentId, Integer courseId){
        this.score = score;
        this.studentId = studentId;
        this.courseId = courseId;
    }

}
