package com.kozetin.library.service;

import com.kozetin.library.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Book> getBookList() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT id, ISN, name, author FROM book",
                (rs, rowNum) -> new Book(rs.getLong("id"), rs.getLong("ISN"), rs.getString("name"), rs.getString("author"))
        ));
    }

    public void removeBook(String bookid) {
        Long id = Long.parseLong(bookid);
        jdbcTemplate.update("DELETE FROM book " +
                "WHERE book.id = ?",id);
        jdbcTemplate.update("DELETE FROM book_user " +
                "WHERE book_user.book_id = ?",id);
    }
}
