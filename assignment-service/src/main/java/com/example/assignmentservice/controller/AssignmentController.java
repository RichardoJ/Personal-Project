package com.example.assignmentservice.controller;

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

import com.example.assignmentservice.exception.NotFoundException;
import com.example.assignmentservice.model.Assignment;
import com.example.assignmentservice.service.AssignmentService;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    private final AssignmentService assignment_service;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignment_service = assignmentService;
    }

    @GetMapping("")
    public List<Assignment> all() {
        return assignment_service.listAllAssignment();
    }

    @GetMapping("/{id}")
    EntityModel<Assignment> one(@PathVariable Integer id) {
        Assignment assignment = assignment_service.getAssignment(id);
        if (assignment == null) {
            throw new NotFoundException(id);
        } else {
            return EntityModel.of(assignment, linkTo(methodOn(AssignmentController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(AssignmentController.class).all()).withRel("assignment"));
        }
    }

    @PostMapping("/list/course")
    public List<Assignment> allByCourseId(@RequestBody Integer[] listID){
        return assignment_service.listAllAssignmentByCourseId(List.of(listID));
    }

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Assignment> add(@RequestBody Assignment assignment_tmp) {
        Assignment persistedAssignment = assignment_service.saveAssignment(assignment_tmp);
        return ResponseEntity
                .created(URI.create(String.format("/assignment/%s", persistedAssignment.getAssignment_name())))
                .body(persistedAssignment);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Integer id) {
        assignment_service.deleteAssignment(id);
    }
}
