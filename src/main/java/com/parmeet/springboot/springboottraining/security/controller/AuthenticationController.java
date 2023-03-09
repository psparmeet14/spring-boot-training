package com.parmeet.springboot.springboottraining.security.controller;

import com.parmeet.springboot.springboottraining.security.configuration.JwtService;
import com.parmeet.springboot.springboottraining.security.dao.UserDao;
import com.parmeet.springboot.springboottraining.security.dto.AuthenticationRequest;
import com.parmeet.springboot.springboottraining.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
//        return ResponseEntity.status(400).body("Some error has occurred");
    }

}
