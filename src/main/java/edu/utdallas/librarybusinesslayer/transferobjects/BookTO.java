package edu.utdallas.librarybusinesslayer.transferobjects;

import edu.utdallas.daolayer.entities.AuthorEntity;

import java.util.List;

public class BookTO {

    private String bookIsbn;

    private String bookTitle;

    private List<AuthorEntity> bookAuthors;

    private boolean available;


    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public List<AuthorEntity> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<AuthorEntity> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
