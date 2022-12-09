package com.example.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public void setUserClaims(String uid, List<Permission> requestedPermissions) throws FirebaseAuthException {
        List<String> permissions = requestedPermissions
                .stream()
                .map(Enum::toString)
                .collect(Collectors.toList());

        Map<String, Object> claims = Map.of("custom_claims", permissions);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    public void setStudentClaims(String uid) throws FirebaseAuthException {
        List<String> permissions = new ArrayList<>();
        permissions.add("STUDENT");

        Map<String, Object> claims = Map.of("custom_claims", permissions);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    public void setTeacherClaims(String uid) throws FirebaseAuthException {
        List<String> permissions = new ArrayList<>();
        permissions.add("TEACHER");

        Map<String, Object> claims = Map.of("custom_claims", permissions);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    public String getToken(String uid) throws FirebaseAuthException{
       return firebaseAuth.createCustomToken(uid);
    }

    public Boolean validateToken(String token) throws FirebaseAuthException {
        System.out.println(token);
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
        String uid = decodedToken.getUid();
        if (uid != null) {
            return true;
        }
        else{
            return false;
        }
    }

    public void deleteUser(String uid) throws FirebaseAuthException {
        firebaseAuth.deleteUser(uid);
    }
}