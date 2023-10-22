package com.parmeet.springboottraining.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByEmail(String email) {
        try {
            var result = jdbcTemplate.queryForObject("SELECT * FROM APP_USER WHERE EMAIL = ?",
                    new UserMapper(), email);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public User save(User user) {
        jdbcTemplate.update("INSERT INTO APP_USER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, ROLE) VALUES(?,?,?,?,?)",
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole().name());
        return user;
    }
}
