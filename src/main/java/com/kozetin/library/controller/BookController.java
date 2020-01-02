package com.kozetin.library.controller;

import com.kozetin.library.domain.Book;
import com.kozetin.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller

public class BookController {
    @Autowired
    private BookService bookService;

    private void getListModel(Model model) {
        List<Book> bookList = new ArrayList<>(bookService.getBookList());
        model.addAttribute(bookList);
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
    public String removeBook (Model model, @RequestParam("bookId") String bookId) {
        bookService.removeBook(bookId);
        return "redirect:/books";
    }
}
