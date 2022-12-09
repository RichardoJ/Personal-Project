package com.example.classservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.classservice.model.CourseTeacher;
import com.example.classservice.service.CourseTeacherService;

@RestController
@RequestMapping("/course/teacher")
public class CourseTeacherController {
    private final CourseTeacherService courseTeacherService;

    public CourseTeacherController(CourseTeacherService courseTeacherService){
        this.courseTeacherService = courseTeacherService;
    }

    @GetMapping("/{id}")
    public List<CourseTeacher> all(@PathVariable Integer id){
        return courseTeacherService.listAllByTeacherId(id);
    }
}
