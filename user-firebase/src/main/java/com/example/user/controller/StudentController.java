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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import javax.persistence.criteria.Fetch;

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

    public StudentController(StudentService studentService) {
        this.student_service = studentService;
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

    @GetMapping("/{id}")
    EntityModel<Student> one(@PathVariable Integer id) {
        Student student = student_service.getStudent(id);
        if (student == null) {
            throw new NotFoundException(id);
        }
        return EntityModel.of(student, linkTo(methodOn(StudentController.class).one(id)).withSelfRel(), linkTo(methodOn(StudentController.class).all()).withRel("student"));
    }

    // @GetMapping("/rabbitMQ")
    // public ResponseEntity<?> getStudents() throws JsonProcessingException {
    //     Student student = queueConsumer.processMessage();
    //     System.out.println("Message received from " + queueConsumer.getQueueName() + " : " + student);
    //     return new ResponseEntity<Student>(student, HttpStatus.OK);
    // }

    @PostMapping(value = "/rabbitMQ/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> storeStudent(@RequestBody Student student) throws JsonProcessingException {
        queueProducer.produce(student);
        return new ResponseEntity<Student>(student, HttpStatus.CREATED);
    }

    // @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    // public ResponseEntity<?> storeStudent(@RequestBody Student student) throws JsonProcessingException {
    //     template.convertAndSend(RabbitConfiguration.EXCHANGE, RabbitConfiguration.ROUTINGKEY, student);
    //     return new ResponseEntity<Student>(student, HttpStatus.CREATED);
    // }

    // @GetMapping("/{id}")
    // ResponseEntity<?> getGroup(@PathVariable Integer id) {
    //     Optional<Student> student = student_service.getStudent(id);
    //     return student.map(response -> ResponseEntity.ok().body(response))
    //             .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Integer id) {
        student_service.deleteStudent(id);
    }
}