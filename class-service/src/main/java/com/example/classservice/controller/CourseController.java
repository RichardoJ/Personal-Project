package com.example.classservice.controller;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.classservice.exception.NotFoundException;
import com.example.classservice.model.Course;
import com.example.classservice.model.CourseTeacher;
import com.example.classservice.service.CourseService;
import com.example.classservice.service.CourseTeacherService;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService course_service;
    private final CourseTeacherService courseTeacherService;
    private final WebClient webClient;

    public CourseController(CourseService courseService, CourseTeacherService courseTeacherService, WebClient webClient){
        this.course_service = courseService;
        this.courseTeacherService = courseTeacherService;
        this.webClient =  webClient;
    }


    @GetMapping("")
    public List<Course> all() {
        return course_service.listAllCourse();
    }

    @GetMapping("/{id}")
    EntityModel<Course> one(@PathVariable Integer id) {
        Course course = course_service.getCourse(id);
        if(course == null){
            throw new NotFoundException(id);
        }

        return EntityModel.of(course, linkTo(methodOn(CourseController.class).one(id)).withSelfRel(),
                linkTo(methodOn(CourseController.class).all()).withRel("course"));
    }

    @PostMapping(value = "/add/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Course> add(@RequestBody Course course_tmp, @PathVariable Integer id) {
        Course persistedCourse = course_service.saveCourse(course_tmp);
        CourseTeacher teacherCourse = new CourseTeacher();
        teacherCourse.setCourse_teacher(course_tmp);
        teacherCourse.setTeacher_id_enrollment(id);
        courseTeacherService.enrollCourseTeacher(teacherCourse);
        return ResponseEntity.created(URI.create(String.format("/course/%s", persistedCourse.getId())))
                .body(persistedCourse);
    }

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable Integer id) {
        course_service.deleteCourse(id);
        String link = "http://localhost:8083/assignment/course/" + id;
        webClient.delete().uri(link);
    }
}
