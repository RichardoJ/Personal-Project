package com.example.user.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.model.NotFoundException;
import com.example.user.model.Student;
import com.example.user.model.Teacher;
import com.example.user.service.StudentService;
import com.example.user.service.TeacherService;
import com.example.user.service.UserManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StudentService student_service;
    private final TeacherService teacher_service;
    private final UserManagementService userManagementService;

    public AuthController(StudentService studentService, TeacherService teacherService,
            UserManagementService userManagementService) {
        this.student_service = studentService;
        this.teacher_service = teacherService;
        this.userManagementService = userManagementService;
    }

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping(path = "/test")
    @PreAuthorize("hasAuthority('READ')")
    public String test(Principal principal) {
        return principal.getName();
    }

    @GetMapping(path = "/login/{email}")
    public ResponseEntity<?> findEntity(@PathVariable String email) {
        Student student = student_service.getStudentByEmail(email);
        Teacher teacher = teacher_service.getTeacherByEmail(email);

        if (student == null) {
            if (teacher == null) {
                throw new NotFoundException(404);
            } else {
                Map<String, String> map = new HashMap<String, String>(1);
                map.put("role", "TEACHER");
                map.put("ID", Integer.toString(teacher.getId()));
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        }
        Map<String, String> map = new HashMap<String, String>(1);
        map.put("role", "STUDENT");
        map.put("ID", Integer.toString(student.getId()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(path = "/validate")
    public ResponseEntity<?> validateToken(@RequestBody String token) throws FirebaseAuthException {
        // return new ResponseEntity<String>(token, HttpStatus.OK);
        Boolean status = userManagementService.validateToken(token);
        if (status == true) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}


