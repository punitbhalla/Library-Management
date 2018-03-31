package edu.utdallas.daolayer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "AUTHORS")
public class AuthorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Author_id")
    private int bookAuthorId;

    @Column(name = "Name")
    private String bookAuthorName;

    @ManyToMany(mappedBy="bookAuthors",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BookEntity> books;

    public int getBookAuthorId() {
        return bookAuthorId;
    }

    public void setBookAuthorId(int bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public void setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
}
