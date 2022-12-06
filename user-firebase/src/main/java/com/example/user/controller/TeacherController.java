package com.example.user.controller;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.model.NotFoundException;
import com.example.user.model.Teacher;
import com.example.user.service.TeacherService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacher_service;

    public TeacherController(TeacherService teacherService) {
        this.teacher_service = teacherService;
    }

    @GetMapping("")
    public List<Teacher> all() {
        return teacher_service.listAllTeacher();
    }
    

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Teacher> add(@RequestBody Teacher user) {
        Teacher persistedTeacher = teacher_service.saveTeacher(user);
        return ResponseEntity.created(URI.create(String.format("/Teacher/%s", persistedTeacher.getTeacher_name())))
                .body(persistedTeacher);
    }

    @GetMapping("/{id}")
    EntityModel<Teacher> one(@PathVariable Integer id) {
        Teacher Teacher = teacher_service.getTeacher(id);
        if (Teacher == null) {
            throw new NotFoundException(id);
        }
        return EntityModel.of(Teacher, linkTo(methodOn(TeacherController.class).one(id)).withSelfRel(), linkTo(methodOn(TeacherController.class).all()).withRel("Teacher"));
    }
    
}
