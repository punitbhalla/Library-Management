package edu.utdallas.librarybusinesslayer.transferobjects;

import java.util.Date;

public class LoanTO {
    private int loanID;
    private int cardID;
    private String borrowerName;
    private String isbn;
    private String bookTitle;
    private Date bookDueDate;
    private Date bookDateOut;

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getBookDueDate() {
        return bookDueDate;
    }

    public void setBookDueDate(Date bookDueDate) {
        this.bookDueDate = bookDueDate;
    }

    public Date getBookDateOut() {
        return bookDateOut;
    }

    public void setBookDateOut(Date bookDateOut) {
        this.bookDateOut = bookDateOut;
    }
}
