package com.example.classservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.classservice.model.Course;
import com.example.classservice.model.Enrollment;
import com.example.classservice.repository.CourseRepository;
import com.example.classservice.repository.EnrollmentRepository;

@Service
@Transactional
public class EnrollmentService {
    private final  EnrollmentRepository enrollmentrepo;
    private final CourseRepository courserepo;

    public  EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository){
        this.enrollmentrepo = enrollmentRepository;
        this.courserepo = courseRepository;
    }
    
    public List<Enrollment> listAllEnrollment(){
        return enrollmentrepo.findAll();
    }

    public Enrollment saveEnrollment(Enrollment user){
        return enrollmentrepo.save(user);
    }

    public Enrollment getEnrollment(Integer id) {
        Optional<Enrollment> tmp = enrollmentrepo.findById(id);
        if(tmp.isEmpty()){
            return null;
        }else{
            return tmp.get();
        }
    }

    public List<Enrollment> getEnrollmentByStudentId(Integer id){
        List<Enrollment> enrollment = enrollmentrepo.findByStudentId(id);
        return enrollment;
    }

    public List<Course> getCourseByStudentId(Integer id){
        List<Enrollment> enrollments = getEnrollmentByStudentId(id);
        List<Course> student_course = new ArrayList<>();
        for (Enrollment enroll : enrollments){
            Course tmp = courserepo.findById(enroll.getCourse_enrollment().getId()).get();
            student_course.add(tmp);
        }
        return student_course;
    }

    public List<Integer> getCourseIdByStudentId(Integer id){
        List<Course> course = getCourseByStudentId(id);
        List<Integer> result = new ArrayList<>();
        for (Course course_tmp : course){
            result.add(course_tmp.getId());
        }
        return result;
    }

    public void deleteEnrollment(Integer id){
        enrollmentrepo.deleteById(id);
    }
}
