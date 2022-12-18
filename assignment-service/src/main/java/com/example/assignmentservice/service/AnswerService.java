package com.example.assignmentservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.assignmentservice.model.Answer;
import com.example.assignmentservice.model.CourseAnswer;
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

    public List<CourseAnswer> getCourseAnswer(Integer student_id, Integer course_id){
        Optional<List<CourseAnswer>> answer = answerrepo.findCourseAnswer(student_id, course_id);
        
        if(answer.isPresent()){
            System.out.print(answer.get());
            return answer.get();
        }else{
            System.out.println("KOSONG");
            return null;
        }
    }

    public Float getAverageCourseAnswer(List<CourseAnswer> answers){
        Float average = 0.0f;
        Float total = 0.0f;
        for (CourseAnswer answer : answers) {
            total += answer.getScore();
            System.out.println("Total Average : " + total);
        }
        average = (float) (total / answers.size());
        System.out.println("Hasil Akhir : " + average);
        return average;
    }

    public String checkGoodOrBad(Float value){
        if (value >= 80.0f && value <= 100.0f){
            return "Proficient";
        }else if(value < 80.0f && value >= 65.0f){
            return "Beginning";
        }else if (value < 65.0f && value >= 0.0f){
            return "Orienting";
        }else{
            return "Not Started yet";
        }
    }

    public void deleteAnswer(Integer id){
        answerrepo.deleteById(id);
    }
}
