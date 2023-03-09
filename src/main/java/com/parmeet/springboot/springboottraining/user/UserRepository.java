package com.parmeet.springboot.springboottraining.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM _USER WHERE EMAIL = ?",
                new Object[]{email}, new UserMapper());

    }
}
