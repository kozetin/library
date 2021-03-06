package com.kozetin.library.controller;

import com.kozetin.library.domain.Book;
import com.kozetin.library.service.BookService;
import com.kozetin.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller

public class BookController {
    @Autowired
    private BookService bookService;

    private void getListModel(Model model) {
        List<Book> bookList = new ArrayList<>(bookService.getBookList());
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("bookList", bookList);
        model.addAttribute("currentUser",currentUser);
    }

    @GetMapping
    public String redirect()
    {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String getBookList(Model model) {
        getListModel(model);
        return "books";
    }

    @PostMapping("/removeBook")
    public String removeBook (@RequestParam("bookId") String bookId) {
        bookService.removeBook(bookId);
        return "redirect:/books";
    }

    @PostMapping("/editBook")
    public String editBook(Model model, @RequestParam("bookId") String bookId, @RequestParam("bookIsn") String bookISN, @RequestParam("bookName") String bookName, @RequestParam("bookAuthor") String bookAuthor) {
        String errorMessage = bookService.editBook(bookId,bookISN,bookName,bookAuthor);
        if (!errorMessage.equals("OK")) {
            model.addAttribute("errorMessage", errorMessage);
            getListModel(model);
            return "books";
        }
        return "redirect:/books";
    }

    @PostMapping("/addBook")
    public String addBook(Model model, @RequestParam("bookIsn") String bookISN, @RequestParam("bookName") String bookName, @RequestParam("bookAuthor") String bookAuthor) {
        String errorMessage = bookService.addBook(bookISN,bookName,bookAuthor);
        if (!errorMessage.equals("OK")) {
            model.addAttribute("errorMessage", errorMessage);
            getListModel(model);
            return "books";
        }
        return "redirect:/books";
    }

    @PostMapping("/takeBook")
    public String takeBook (@RequestParam("bookId") String bookId) {
        bookService.takeBook(bookId,SecurityContextHolder.getContext().getAuthentication().getName());
        return "redirect:/books";
    }

    @PostMapping("/leaveBook")
    public String leaveBook (@RequestParam("bookId") String bookId) {
        bookService.leaveBook(bookId);
        return "redirect:/books";
    }
}
