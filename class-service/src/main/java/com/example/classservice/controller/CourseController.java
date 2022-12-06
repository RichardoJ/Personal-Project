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

import com.example.classservice.exception.NotFoundException;
import com.example.classservice.model.Course;
import com.example.classservice.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService course_service;

    public CourseController(CourseService courseService){
        this.course_service = courseService;
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

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Course> add(@RequestBody Course course_tmp) {
        Course persistedCourse = course_service.saveCourse(course_tmp);
        return ResponseEntity.created(URI.create(String.format("/course/%s", persistedCourse.getCourse_name())))
                .body(persistedCourse);
    }

    // @GetMapping("/{id}/modules")
    // public List<Module> all_modules(@PathVariable Integer id) {
    //     List<Module> modules = course_service.getCourseModules(id);
    //     if(modules == null){
    //         throw new NotFoundException(id);
    //     }else{
    //         return modules;
    //     }
    // }

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable Integer id) {
        course_service.deleteCourse(id);
    }
}
