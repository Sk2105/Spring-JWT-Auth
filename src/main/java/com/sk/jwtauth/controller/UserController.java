package com.sk.jwtauth.controller;


import com.sk.jwtauth.entities.LoginCredentials;
import com.sk.jwtauth.entities.User;
import com.sk.jwtauth.security.JWTUtils;
import com.sk.jwtauth.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {


    @Autowired
    private UserServices userServices;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public String login(@RequestBody LoginCredentials loginCredentials) throws UsernameNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginCredentials.email(),
                        loginCredentials.password()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtUtils.generateToken(loginCredentials.email());
        } else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }


    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userServices.registerUser(user);
    }
}
