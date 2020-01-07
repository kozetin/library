package com.kozetin.library.controller;

import com.kozetin.library.domain.Book;
import com.kozetin.library.domain.User;
import com.kozetin.library.service.BookService;
import com.kozetin.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller

public class UserController {

    @Autowired
    private UserService userService;

    private void getListModel(Model model) {
        List<User> userList = new ArrayList<>(userService.getUserList());
        model.addAttribute("userList", userList);
    }

    @GetMapping("/users")
    public String getBookList(Model model) {
        getListModel(model);

        return "users";
    }

    @PostMapping("/removeUser")
    public String removeBook (@RequestParam("userId") String userId) {
        userService.removeUser(userId);
        return "redirect:/users";
    }

    @PostMapping("/editUser")
    public String editBook(Model model, @RequestParam("userId") String userId, @RequestParam("login") String login, @RequestParam("userPassword") String userPassword) {
        String errorMessage = userService.editUser(userId,login,userPassword);
        if (!errorMessage.equals("OK")) {
            model.addAttribute("errorMessage", errorMessage);
            getListModel(model);
            return "users";
        }
        return "redirect:/users";
    }

    @PostMapping("/addUser")
    public String addBook(Model model, @RequestParam("login") String login, @RequestParam("userPassword") String userPassword) {
        String errorMessage = userService.addUser(login,userPassword);
        if (!errorMessage.equals("OK")) {
            model.addAttribute("errorMessage", errorMessage);
            getListModel(model);
            return "users";
        }
        return "redirect:/users";
    }

}
