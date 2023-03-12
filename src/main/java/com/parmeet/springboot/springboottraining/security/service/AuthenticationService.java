package com.parmeet.springboot.springboottraining.security.service;

import com.parmeet.springboot.springboottraining.security.configuration.JwtService;
import com.parmeet.springboot.springboottraining.security.dao.UserDao;
import com.parmeet.springboot.springboottraining.security.dto.AuthenticationRequest;
import com.parmeet.springboot.springboottraining.security.dto.AuthenticationResponse;
import com.parmeet.springboot.springboottraining.security.dto.RegisterRequest;
import com.parmeet.springboot.springboottraining.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {

        return null;
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        final UserDetails user = userDao.findUserByEmail(request.getEmail());
//        if (user != null) {
//            return ResponseEntity.ok(jwtService.generateToken(user));
//        }
//        return ResponseEntity.status(400).body("Some error has occurred");
        return null;
    }

}
