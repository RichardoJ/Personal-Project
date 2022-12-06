package com.example.classservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
import com.example.classservice.model.Enrollment;
import com.example.classservice.service.EnrollmentService;


@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollment_service;

    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollment_service = enrollmentService;
    }

    @GetMapping("")
    public List<Enrollment> all() {
        return enrollment_service.listAllEnrollment();
    }

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Enrollment> add(@RequestBody Enrollment user) {
        Enrollment persistedEnrollment = enrollment_service.saveEnrollment(user);
        return ResponseEntity.created(URI.create(String.format("/enrollment/%s", persistedEnrollment.getId())))
                .body(persistedEnrollment);
    }

    @GetMapping("/{id}")
    EntityModel<Enrollment> one(@PathVariable Integer id) {
        Enrollment enrollment = enrollment_service.getEnrollment(id);
        if(enrollment == null){
            throw new NotFoundException(id);
        }
        return EntityModel.of(enrollment, linkTo(methodOn(EnrollmentController.class).one(id)).withSelfRel(), linkTo(methodOn(EnrollmentController.class).all()).withRel("enrollment")); 
    }

    @GetMapping("/{id}/student_course")
    public List<Course> findbystudent(@PathVariable Integer id) {
        return enrollment_service.getCourseByStudentId(id);
    }

    @GetMapping("/{id}/student_course/list")
    public ResponseEntity<?> findbystudentasList(@PathVariable Integer id) {
        List<Integer> data = enrollment_service.getCourseIdByStudentId(id);
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>(1) {{put("id", data);}};
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteEnrollment(@PathVariable Integer id) {
        enrollment_service.deleteEnrollment(id);
    }
}
