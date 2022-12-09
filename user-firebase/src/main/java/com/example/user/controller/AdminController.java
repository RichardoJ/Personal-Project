package com.example.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.service.Permission;
import com.example.user.service.UserManagementService;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserManagementService userManagementService;

    @Secured("ROLE_ANONYMOUS")
    @PostMapping(path = "/user-claims/{uid}")
    public void setUserClaims(
            @PathVariable String uid,
            @RequestBody List<Permission> requestedClaims
    ) throws FirebaseAuthException {
        userManagementService.setUserClaims(uid, requestedClaims);
    }
    
    @PostMapping(path = "/student-claims/{uid}")
    public Map<String, String> setStudentClaims(
            @PathVariable String uid
    ) throws FirebaseAuthException {
        userManagementService.setStudentClaims(uid);
        var token = userManagementService.getToken(uid);
        Map<String, String> map = new HashMap<String, String>(1){{put("token", token);}};
        return map;
    }

    @PostMapping(path = "/teacher-claims/{uid}")
    public Map<String, String> setTeacherClaims(
            @PathVariable String uid
    ) throws FirebaseAuthException {
        userManagementService.setStudentClaims(uid);
        var token = userManagementService.getToken(uid);
        Map<String, String> map = new HashMap<String, String>(1){{put("token", token);}};
        return map;
    }

}