package com.example.assignmentservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.assignmentservice.model.Assignment;
import com.example.assignmentservice.repository.AssignmentRepository;


@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository assignmentrepo;

    public AssignmentService(AssignmentRepository assignmentRepository){
        this.assignmentrepo = assignmentRepository;
    }

    public List<Assignment> listAllAssignment(){
        return assignmentrepo.findAll();
    }

    public List<Assignment> listAllAssignmentByCourseId(List<Integer> course_list){
        List<Assignment> assignments = new ArrayList<>();
        for(Integer id : course_list){
            assignments.addAll(assignmentrepo.findByCourseId(id));
        }
        return assignments;
    }

    public Assignment saveAssignment(Assignment assignment_tmp){
        return assignmentrepo.save(assignment_tmp);
    }

    public Assignment getAssignment(Integer id) {
        Optional<Assignment> assignment = assignmentrepo.findById(id);
        if(assignment.isPresent()){
            return assignment.get();
        }else{
            return null;
        }
    }

    public void deleteAssignment(Integer id){
        assignmentrepo.deleteById(id);
    }
}