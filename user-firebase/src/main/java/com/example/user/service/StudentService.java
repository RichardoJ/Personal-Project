package com.example.user.service;

import com.example.user.model.Student;
import com.example.user.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentrepo;

    public StudentService(StudentRepository studentRepository){
        this.studentrepo = studentRepository;
    }

    public List<Student> listAllStudent(){
        return studentrepo.findAll();
    }

    public Student saveStudent(Student user){
        return studentrepo.save(user);
    }

    public Student getStudent(Integer id) {
        Optional<Student> tmp = studentrepo.findById(id);
        if(tmp.isPresent()){
            return tmp.get();
        }else{
            return null;
        }
    }

    public Student getStudentByEmail(String email) {
        Optional<Student> tmp = studentrepo.findByEmail(email);
        if(tmp.isPresent()){
            return tmp.get();
        }else{
            return null;
        }
    }

    // public Optional<Student> getStudent(Integer id){
    //     Optional<Student> tmp = studentrepo.findById(id);
    //     return tmp;
    // }

    public void deleteStudent(Integer id){
        studentrepo.deleteById(id);
    }
}
