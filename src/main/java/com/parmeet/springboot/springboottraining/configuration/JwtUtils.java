package com.parmeet.springboot.springboottraining.configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public String extractUsername(String jwtToken) {
        return null;
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        return false;
    }
}
