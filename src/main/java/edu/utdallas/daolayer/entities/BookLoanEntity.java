package edu.utdallas.daolayer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "BOOK_LOANS")
public class BookLoanEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Loan_id")
    private int bookLoanID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "Isbn")
    @JsonIgnore
    private BookEntity bookEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "Card_id")
    @JsonIgnore
    private BorrowerEntity borrowerEntity;

    @Column(name = "Date_out")
    private Date bookDateOut;

    @Column(name = "Due_Date")
    private Date bookDueDate;

    @Column(name = "Date_in")
    private Date bookDateIn;

    public int getBookLoanID() {
        return bookLoanID;
    }

    public void setBookLoanID(int bookLoanID) {
        this.bookLoanID = bookLoanID;
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public BorrowerEntity getBorrowerEntity() {
        return borrowerEntity;
    }

    public void setBorrowerEntity(BorrowerEntity borrowerEntity) {
        this.borrowerEntity = borrowerEntity;
    }

    public Date getBookDateOut() {
        return bookDateOut;
    }

    public void setBookDateOut(Date bookDateOut) {
        this.bookDateOut = bookDateOut;
    }

    public Date getBookDueDate() {
        return bookDueDate;
    }

    public void setBookDueDate(Date bookDueDate) {
        this.bookDueDate = bookDueDate;
    }

    public Date getBookDateIn() {
        return bookDateIn;
    }

    public void setBookDateIn(Date bookDateIn) {
        this.bookDateIn = bookDateIn;
    }
}
