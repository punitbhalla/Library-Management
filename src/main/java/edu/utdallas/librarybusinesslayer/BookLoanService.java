package edu.utdallas.librarybusinesslayer;

import edu.utdallas.daolayer.dao.BookDao;
import edu.utdallas.daolayer.dao.BookLoanDao;
import edu.utdallas.daolayer.dao.BorrowerDao;
import edu.utdallas.daolayer.entities.BookEntity;
import edu.utdallas.daolayer.entities.BookLoanEntity;
import edu.utdallas.daolayer.entities.BorrowerEntity;
import edu.utdallas.librarybusinesslayer.transferobjects.LoanTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookLoanService {

    @Autowired
    private BookLoanDao bookLoanDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BorrowerDao borrowerDao;

    public List<LoanTO> getBookLoans() {
        List<BookLoanEntity> bookLoanEntities=bookLoanDao.getBookLoans();
        List<LoanTO> loanTOS=new ArrayList<LoanTO>();
        for(BookLoanEntity bookLoanEntity:bookLoanEntities){
            LoanTO loanTO=new LoanTO();
            loanTO.setLoanID(bookLoanEntity.getBookLoanID());
            loanTO.setCardID(bookLoanEntity.getBorrowerEntity().getBorrowerCardId());
            loanTO.setBorrowerName(bookLoanEntity.getBorrowerEntity().getBorrowerBname());
            loanTO.setIsbn(bookLoanEntity.getBookEntity().getBookIsbn());
            loanTO.setBookTitle(bookLoanEntity.getBookEntity().getBookTitle());
            loanTO.setBookDueDate(bookLoanEntity.getBookDueDate());
            loanTO.setBookDateOut(bookLoanEntity.getBookDateOut());
            loanTOS.add(loanTO);
        }

        return loanTOS;
    }

    public boolean checkMaxBookLoans(int cardID) {
        return bookLoanDao.checkMaxBookLoans(cardID);
    }

    public List<LoanTO> getCheckingBooks(String isbn, String cardID, String borrowerName) {
        List<BookLoanEntity> bookLoanEntities=bookLoanDao.getCheckingBookResult(isbn, cardID, borrowerName);
        List<LoanTO> loanTOS=new ArrayList<LoanTO>();
        for(BookLoanEntity bookLoanEntity:bookLoanEntities){
            LoanTO loanTO=new LoanTO();
            loanTO.setLoanID(bookLoanEntity.getBookLoanID());
            loanTO.setCardID(bookLoanEntity.getBorrowerEntity().getBorrowerCardId());
            loanTO.setBorrowerName(bookLoanEntity.getBorrowerEntity().getBorrowerBname());
            loanTO.setIsbn(bookLoanEntity.getBookEntity().getBookIsbn());
            loanTO.setBookTitle(bookLoanEntity.getBookEntity().getBookTitle());
            loanTO.setBookDueDate(bookLoanEntity.getBookDueDate());
            loanTO.setBookDateOut(bookLoanEntity.getBookDateOut());
            loanTOS.add(loanTO);
        }

        return loanTOS;
    }

    public int insertBookLoan(String isbn, int cardID) {
        // isBookAvailable will return false if that book is already checked out
        /*
        return -1: exception
        return 1: successfully inserted
        return 0: book is already checked out
        return -2: if borrower is trying to checkout more than 3 books
        */
        if (!bookDao.isBookAvailable(isbn)) {
            return 0;
        } else {
            if(!bookLoanDao.checkMaxBookLoans(cardID)){
                return bookLoanDao.insertBookLoan(isbn, cardID);
            }
            return -2;
        }
    }

    public boolean updateBookLoan(int loanID) {

        return bookLoanDao.updateBookLoan(loanID);
    }
}
