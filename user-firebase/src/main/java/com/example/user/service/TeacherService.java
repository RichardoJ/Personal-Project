package com.example.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.user.model.Teacher;
import com.example.user.repository.TeacherRepository;

@Service
@Transactional
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> listAllTeacher(){
        return teacherRepository.findAll();
    }

    public Teacher saveTeacher(Teacher user){
        return teacherRepository.save(user);
    }

    public Teacher getTeacher(Integer id) {
        Optional<Teacher> tmp = teacherRepository.findById(id);
        if(tmp.isPresent()){
            return tmp.get();
        }else{
            return null;
        }
    }

    public Teacher getTeacherByEmail(String email) {
        Optional<Teacher> tmp = teacherRepository.findByEmail(email);
        if(tmp.isPresent()){
            return tmp.get();
        }else{
            return null;
        }
    }

    public void deleteTeacher(Integer id){
        teacherRepository.deleteById(id);
    }

}
