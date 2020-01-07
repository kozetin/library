package com.kozetin.library.service;

import com.kozetin.library.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getUserList() {
        System.out.println(passwordEncoder.encode("pass1"));
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT id, login, password, active FROM usr",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("login"), rs.getString("password"), rs.getBoolean("active"))
        ));
    }

    public void removeUser(String userId) {
        Long id = Long.parseLong(userId);
        jdbcTemplate.update("DELETE FROM usr " +
                "WHERE usr.id = ?",id);
    }

    public String editUser(String userId, String login, String userPassword) {
        Long id = Long.parseLong(userId);
        //data validation
        if (login.isEmpty()) {
            return "Заполните значение Имя пользователя";
        }
        if (userPassword.isEmpty()) {
            return "Заполните значение Пароль";
        }
        

        List<String> loginList = jdbcTemplate.query(
                "SELECT login FROM usr WHERE id != ?", new Object[] {id},
                (rs, rowNum) ->  rs.getString("login"));
        if (loginList.contains(login)) {
            return "Пользователь с таким именем уже существует";
        }
        //end of data validation
        jdbcTemplate.update("UPDATE usr " +
                "SET login=?, password=? " +
                "WHERE id=?",login,passwordEncoder.encode(userPassword),id);

        return "OK";
    }

    public String addUser(String login, String userPassword) {
        //data validation
        if (login.isEmpty()) {
            return "Заполните Имя пользователя";
        }
        if (userPassword.isEmpty()) {
            return "Заполните Пароль";
        }

        List<String> loginList = jdbcTemplate.query(
                "SELECT login FROM usr WHERE login = ?", new Object[] {login},
                (rs, rowNum) ->  rs.getString("login"));
        if (!loginList.isEmpty()) {
            return "Пользователь с таким именем уже существует";
        }
        jdbcTemplate.update("INSERT INTO usr (login, password, active) " +
                "VALUES (?,?,?)",login,passwordEncoder.encode(userPassword), true);
        return "OK";
    }

}
