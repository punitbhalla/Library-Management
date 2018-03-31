package edu.utdallas.daolayer.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
    @Table(name = "BOOK")

    public class BookEntity implements Serializable {

        @Id
        @Column(name = "Isbn")
        private String bookIsbn;

        @Column(name = "Title")
        private String bookTitle;

        @ManyToMany(cascade = {
                CascadeType.ALL
        },fetch = FetchType.EAGER)
        @JoinTable(name="BOOK_AUTHORS",
                joinColumns = @JoinColumn(name="Isbn"),
                inverseJoinColumns = @JoinColumn(name="Author_id"))
        @JsonIgnore
        private List<AuthorEntity> bookAuthors;



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



