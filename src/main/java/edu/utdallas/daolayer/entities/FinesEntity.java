package edu.utdallas.daolayer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FINES")
public class FinesEntity implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Loan_id")
    @JsonIgnore
    private BookLoanEntity bookLoanEntity;

    @Column(name = "Fine_amt")
    private Double bookFineAmount;

    @Column(name = "Paid")
    private boolean bookFinePaid;

    public BookLoanEntity getBookLoanEntity() {
        return bookLoanEntity;
    }

    public void setBookLoanEntity(BookLoanEntity bookLoanEntity) {
        this.bookLoanEntity = bookLoanEntity;
    }

    public Double getBookFineAmount() {
        return bookFineAmount;
    }

    public void setBookFineAmount(Double bookFineAmount) {
        this.bookFineAmount = bookFineAmount;
    }

    public boolean isBookFinePaid() {
        return bookFinePaid;
    }

    public void setBookFinePaid(boolean bookFinePaid) {
        this.bookFinePaid = bookFinePaid;
    }
}
