package com.kozetin.library.domain;

public class Book {
    private Long id, ISN;
    private String author, name;

    public Book(Long id, Long ISN, String author, String name) {
        this.id = id;
        this.ISN = ISN;
        this.author = author;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ISN=" + ISN +
                ", Author='" + author + '\'' +
                ", Name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getISN() {
        return ISN;
    }

    public void setISN(Long ISN) {
        this.ISN = ISN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
