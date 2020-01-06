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
    
}
