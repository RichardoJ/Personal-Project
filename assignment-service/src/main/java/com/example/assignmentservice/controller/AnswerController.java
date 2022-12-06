package com.example.assignmentservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.assignmentservice.exception.NotFoundException;
import com.example.assignmentservice.model.Answer;
import com.example.assignmentservice.service.AnswerService;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answer_service;

    public AnswerController(AnswerService answerService) {
        this.answer_service = answerService;
    }

    @GetMapping("")
    public List<Answer> all() {
        return answer_service.listAllAnswer();
    }

    @GetMapping("/{id}")
    EntityModel<Answer> one(@PathVariable Integer id) {
        Answer answer = answer_service.getAnswer(id);
        if (answer == null) {
            throw new NotFoundException(id);
        } else {
            return EntityModel.of(answer, linkTo(methodOn(AnswerController.class).one(id)).withSelfRel(),
                    linkTo(methodOn(AssignmentController.class).all()).withRel("assignment"));
        }
    }

    @GetMapping("/student/{student_id}/assignment/{assignment_id}")
    public Boolean answerByStudentAndAssignmentId(@PathVariable("student_id") Integer student_id, @PathVariable("assignment_id") Integer assignment_id){
      Answer answer = answer_service.getAnswerByStudentId(student_id, assignment_id);
      System.out.println(answer);
      if(answer != null && answer.getAssignment_status() == 1){
        return true;
      }else{
        return false;
      }
    }

    @PostMapping("/student/{student_id}")
    public List<Answer> answerByAssignmentandStudent(@PathVariable("student_id") Integer student_id, @RequestBody List<Integer> id){
        List<Answer> status = new ArrayList<>();
        for(Integer idAssignment : id){
            Answer answer = answer_service.getAnswerByStudentId(student_id,idAssignment);
            if (answer == null) {
                status.add(null);
            } else {
                status.add(answer);
            }
        }
        return status;
    }

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Answer> add(@RequestBody Answer answer_tmp) {
        Answer persistedAnswer = answer_service.saveAnswer(answer_tmp);
        return ResponseEntity.created(URI.create(String.format("/answer/%s", persistedAnswer.getId())))
                .body(persistedAnswer);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Integer id) {
        answer_service.deleteAnswer(id);
    }
}
