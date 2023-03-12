package com.parmeet.springboot.springboottraining.security.controller;

import com.parmeet.springboot.springboottraining.security.dto.AuthenticationRequest;
import com.parmeet.springboot.springboottraining.security.dto.AuthenticationResponse;
import com.parmeet.springboot.springboottraining.security.dto.RegisterRequest;
import com.parmeet.springboot.springboottraining.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
//        return ResponseEntity.status(400).body("Some error has occurred");
    }

}
