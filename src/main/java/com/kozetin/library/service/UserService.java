package com.kozetin.library.service;

import com.kozetin.library.domain.Book;
import com.kozetin.library.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByName (String name) {
        return jdbcTemplate.query(
                "SELECT id, login  password FROM usr " +
                        "WHERE login =?", new Object[] {name},
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"), true)).get(0);
    }

}
