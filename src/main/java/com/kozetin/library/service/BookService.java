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
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBookList() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT id, ISN, name, author, user_name FROM book",
                (rs, rowNum) -> new Book(rs.getLong("id"), rs.getLong("ISN"), rs.getString("author"), rs.getString("name"), rs.getString("user_name"))
        ));
    }

    public void removeBook(String bookid) {
        Long id = Long.parseLong(bookid);
        jdbcTemplate.update("DELETE FROM book " +
                "WHERE book.id = ?",id);
    }

    public String editBook(String bookid, String bookISN, String bookName, String bookAuthor) {
        Long id = Long.parseLong(bookid);
        Long isn;
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

        try {
            isn = Long.parseLong(bookISN);
        } catch (NumberFormatException ex) {
            return "Поле ISN должно быть числовым";
        }

        List<Long> isnList = jdbcTemplate.query(
                "SELECT ISN FROM book WHERE id != ?", new Object[] {id},
                (rs, rowNum) ->  rs.getLong("ISN"));
        if (isnList.contains(isn)) {
            return "Книга с таким ISN уже существует";
        }
        //end of data validation
        jdbcTemplate.update("UPDATE book " +
                "SET ISN=?, name=?, author=? " +
                "WHERE id=?",isn,bookName,bookAuthor,id);

        return "OK";
    }

    public String addBook(String bookISN, String bookName, String bookAuthor) {
        Long isn;
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

        try {
            isn = Long.parseLong(bookISN);
        } catch (NumberFormatException ex) { //throws when id can't be converted
            return "Поле ISN должно быть числовым";
        }

        List<Long> isnList = jdbcTemplate.query(
                "SELECT ISN FROM book WHERE iSN = ?", new Object[] {isn},
                (rs, rowNum) ->  rs.getLong("ISN")); // get full isn list to find duplicates
        if (isnList.contains(isn)) {
            return "Книга с таким ISN уже существует";
        }
        jdbcTemplate.update("INSERT INTO book (isn, name, author) " +
                "VALUES (?,?,?)",isn,bookName,bookAuthor);
        return "OK";
    }

    public void takeBook(String bookId, String username) {
        jdbcTemplate.update("UPDATE book " +
                "SET user_name=? " +
                "WHERE id=?",username,bookId);
    }

    public void leaveBook(String bookId) {
        jdbcTemplate.update("UPDATE book " +
                "SET user_name=? " +
                "WHERE id=?",null,bookId);
    }

}
