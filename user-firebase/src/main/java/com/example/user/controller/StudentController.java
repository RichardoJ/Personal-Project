package com.example.user.controller;

import com.example.user.QueueProducer;
import com.example.user.model.NotFoundException;
import com.example.user.model.Student;
import com.example.user.service.StudentService;
import com.example.user.service.UserManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private QueueProducer queueProducer;

    // @Autowired
    // private RabbitTemplate template;

    private final StudentService student_service;
    private final UserManagementService userManagementService;

    public StudentController(StudentService studentService, UserManagementService userManagementService) {
        this.student_service = studentService;
        this.userManagementService = userManagementService;
    }

    @GetMapping("")
    // @PreAuthorize("hasAuthority('STUDENT')")
    public List<Student> all() {
        return student_service.listAllStudent();
    }
    

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Student> add(@RequestBody Student user) throws FirebaseAuthException {
        Student persistedStudent = student_service.saveStudent(user);
        return ResponseEntity.created(URI.create(String.format("/student/%s", persistedStudent.getId())))
                .body(persistedStudent);
    }

    @PostMapping(value = "/{uid}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Student> addWithClaims(@RequestBody Student user, @PathVariable String uid) throws FirebaseAuthException {
        user.setUid(uid);
        Student persistedStudent = student_service.saveStudent(user);
        userManagementService.setStudentClaims(uid);
        return ResponseEntity.created(URI.create(String.format("/student/%s", persistedStudent.getId())))
                .body(persistedStudent);
    }

    @GetMapping("/{id}")
    EntityModel<Student> one(@PathVariable Integer id) {
        Student student = student_service.getStudent(id);
        if (student == null) {
            throw new NotFoundException(id);
        }
        return EntityModel.of(student, linkTo(methodOn(StudentController.class).one(id)).withSelfRel(), linkTo(methodOn(StudentController.class).all()).withRel("student"));
    }

    @PostMapping(value = "/rabbitMQ/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> storeStudent(@RequestBody Student student) throws JsonProcessingException {
        queueProducer.produce(student);
        return new ResponseEntity<Student>(student, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable("id") Integer id) throws FirebaseAuthException {
        Student tmp = student_service.getStudent(id);
        userManagementService.deleteUser(tmp.getUid());
        student_service.deleteStudent(id);
    }
}
