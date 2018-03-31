package edu.utdallas.daolayer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "BORROWER")
public class BorrowerEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Card_id")
    private int borrowerCardId;

    @Column(name = "Ssn",unique = true)
    private String borrowerSsn;

    @Column(name = "Bname")
    private String borrowerBname;

    @Column(name = "Address")
    private String borrowerAddress;

    @Column(name = "Phone")
    private String borrowerPhone;

    @OneToMany(mappedBy = "borrowerEntity",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BookLoanEntity> loans;

    public List<BookLoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<BookLoanEntity> loans) {
        this.loans = loans;
    }

    public int getBorrowerCardId() {
        return borrowerCardId;
    }

    public void setBorrowerCardId(int borrowerCardId) {
        this.borrowerCardId = borrowerCardId;
    }

    public String getBorrowerSsn() {
        return borrowerSsn;
    }

    public void setBorrowerSsn(String borrowerSsn) {
        this.borrowerSsn = borrowerSsn;
    }

    public String getBorrowerBname() {
        return borrowerBname;
    }

    public void setBorrowerBname(String borrowerBname) {
        this.borrowerBname = borrowerBname;
    }

    public String getBorrowerAddress() {
        return borrowerAddress;
    }

    public void setBorrowerAddress(String borrowerAddress) {
        this.borrowerAddress = borrowerAddress;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone = borrowerPhone;
    }
}
