package com.example.classservice.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.classservice.model.Course;
import com.example.classservice.repository.CourseRepository;
import com.example.classservice.repository.CourseTeacherRepository;
import com.example.classservice.repository.EnrollmentRepository;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courserepo;
    private final CourseTeacherRepository courseTeacherRepo;
    private final EnrollmentRepository enrollmentRepo;

    public CourseService(CourseRepository courseRepository, CourseTeacherRepository courseTeacherRepository, EnrollmentRepository enrollmentRepository){
        this.courserepo = courseRepository;
        this.courseTeacherRepo = courseTeacherRepository;
        this.enrollmentRepo = enrollmentRepository;
    }

    public List<Course> listAllCourse(){
        return courserepo.findAll();
    }

    public Course saveCourse(Course course_tmp){
        return courserepo.save(course_tmp);
    }

    public Course getCourse(Integer id) {
        Optional<Course> course = courserepo.findById(id);
        if(course.isPresent()){
            return course.get();
        }else{
            return null;
        }
    }

    

    // public List<Module> getCourseModules(Integer id) {
    //     Optional<Course> course = courserepo.findById(id);
    //     if(course.isPresent()){
    //         Course final_course = course.get();
    //         return final_course.get;
    //     }else{
    //         return null;
    //     }
    // }

    public void deleteCourse(Integer id){
        courseTeacherRepo.deleteById(id);
        enrollmentRepo.deleteByCourseId(id);
        courserepo.deleteById(id);
    }
}
