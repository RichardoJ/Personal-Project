package com.example.assignmentservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.assignmentservice.model.Answer;
import com.example.assignmentservice.repository.AnswerRepository;

@Service
@Transactional
public class AnswerService {
    private final AnswerRepository answerrepo;

    public AnswerService(AnswerRepository answerRepository){
        this.answerrepo = answerRepository;
    }

    public List<Answer> listAllAnswer(){
        return answerrepo.findAll();
    }

    public Answer saveAnswer(Answer answer_tmp){
        return answerrepo.save(answer_tmp);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = answerrepo.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }else{
            return null;
        }
    }

    public Answer getAnswerByStudentId(Integer student_id, Integer assignment_id){
        Optional<Answer> answer = answerrepo.findAnswerByStudentID(student_id,assignment_id);
        if(answer.isPresent()){
            return answer.get();
        }else{
            return null;
        }
    }

    public void deleteAnswer(Integer id){
        answerrepo.deleteById(id);
    }
}
