package com.kozetin.library.service;

import com.kozetin.library.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    }

    public String editBook(String bookid, String bookISN, String bookName, String bookAuthor) {
        Long id = Long.parseLong(bookid);
        //data validation
        if (bookISN.isEmpty()) {
            return "Заполните значение ISN";
        }
        if (bookName.isEmpty()) {
            return "Заполните значение Название";
        }
        if (bookAuthor.isEmpty()) {
            return "Заполните значение Автор";
        }
        List<Long> isnList = jdbcTemplate.query(
                "SELECT ISN FROM book WHERE id != ?", new Object[] {id},
                (rs, rowNum) ->  rs.getLong("ISN"));
        if (isnList.contains(Long.parseLong(bookISN))) {
            return "Книга с таким ISN уже существует";
        }
        //end of data validation
        jdbcTemplate.update("UPDATE book " +
                "SET ISN=?, name=?, author=? " +
                "WHERE id=?",Long.parseLong(bookISN),bookName,bookAuthor,id);

        return "OK";
    }
}
