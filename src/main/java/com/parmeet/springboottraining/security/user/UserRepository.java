package com.parmeet.springboottraining.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM APP_USER WHERE EMAIL = ?",
                new UserMapper(), email));
    }

    public User save(User user) {
        jdbcTemplate.update("INSERT INTO APP_USER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) VALUES(?,?,?,?,?)",
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole().name());
        return user;
    }
}
