package com.example.classservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.classservice.model.CourseTeacher;
import com.example.classservice.repository.CourseTeacherRepository;

@Service
@Transactional
public class CourseTeacherService {
    private final CourseTeacherRepository courseTeacherRepository;

    public CourseTeacherService(CourseTeacherRepository courseTeacherRepository){
        this.courseTeacherRepository = courseTeacherRepository;
    }

    public List<CourseTeacher> listAllCourseTeacher(){
        return courseTeacherRepository.findAll();
    }

    public CourseTeacher enrollCourseTeacher(CourseTeacher course_tmp){
        return courseTeacherRepository.save(course_tmp);
    }

    public List<CourseTeacher> listAllByTeacherId(Integer teacher_id){
        return courseTeacherRepository.findByTeacherId(teacher_id);
    }

    public CourseTeacher getCourseTeacher(Integer id) {
        Optional<CourseTeacher> course = courseTeacherRepository.findById(id);
        if(course.isPresent()){
            return course.get();
        }else{
            return null;
        }
    }

    public void deleteCourseTeacher(Integer id){
        System.out.println("ID YANG MASUK :" + id);
        if(courseTeacherRepository.findByCourseId(id).isPresent()){
            CourseTeacher tmp = courseTeacherRepository.findByCourseId(id).get();
            Integer deleteId = tmp.getId();
            System.out.println("ID YANG MAU DI DELETE :" + deleteId);
            courseTeacherRepository.deleteById(deleteId);
        }
    }
}
