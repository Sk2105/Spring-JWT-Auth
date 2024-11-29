package com.sk.jwtauth.controller;

import com.sk.jwtauth.entities.User;
import com.sk.jwtauth.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Collections.emptyList;

@RequestMapping("/user")
@RestController
public class MyController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userServices.getAllUsers());
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(emptyList());
        }

    }
}
