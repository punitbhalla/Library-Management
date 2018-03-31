package edu.utdallas.daolayer.dao;

import edu.utdallas.daolayer.entities.BookEntity;
import edu.utdallas.daolayer.entities.BookLoanEntity;
import edu.utdallas.daolayer.entities.BorrowerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Repository
public class BookLoanDao {
    @Autowired
    private SessionFactory sessionFactory;


    public List<BookLoanEntity> getBookLoans() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BookLoanEntity> bookLoans = session.createNativeQuery("SELECT * FROM BOOK_LOANS where Date_in IS NULL", BookLoanEntity.class).getResultList();
            return bookLoans;
        } finally {
            session.close();
        }
    }


    public boolean checkMaxBookLoans(int cardID) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = builder.createQuery(Long.class);
            Root<BookLoanEntity> fromBookLoanEntity = cq.from(BookLoanEntity.class);
            cq.select(builder.count(fromBookLoanEntity)).where(builder.and(
                    builder.isNull(
                            fromBookLoanEntity.get("bookDateIn")),
                    builder.equal(
                            fromBookLoanEntity.get("borrowerEntity").get("borrowerCardId"), cardID)));
            boolean flag;
            if (session.createQuery(cq).getSingleResult() >= 3) {
                flag = true;
            } else {
                flag = false;
            }

            return flag;
        } finally {
            session.close();
        }
    }

    // for populating the checking book screen

    public List<BookLoanEntity> getCheckingBookResult(String isbn, String cardID, String borrowerName) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BookLoanEntity> loans = session.createNativeQuery("(select bl.* from BOOK_LOANS bl where bl.Date_in IS NULL and (bl.Isbn='" + isbn + "' or bl.Card_id=" + cardID + ")) UNION (select bl.* from BOOK_LOANS bl, BORROWER b where bl.Card_id=b.Card_id and bl.Date_in IS NULL and b.Bname like '%" + borrowerName + "%')", BookLoanEntity.class).getResultList();
            return loans;
        } finally {
            session.close();
        }
    }


    public int insertBookLoan(String isbn, int cardID) {
        Session session = null;
        int result;
        try {
            session = sessionFactory.openSession();
            Transaction tx=session.beginTransaction();
            BookLoanEntity bookLoanEntity = new BookLoanEntity();
            BookEntity book = session.createNativeQuery("SELECT * FROM BOOK WHERE Isbn = '" + isbn+"'", BookEntity.class).getSingleResult();
            bookLoanEntity.setBookEntity(book);
            BorrowerEntity borrower = session.createNativeQuery("SELECT * FROM BORROWER WHERE Card_Id = " + cardID, BorrowerEntity.class).getSingleResult();
            bookLoanEntity.setBorrowerEntity(borrower);
            Date date = new Date();
            bookLoanEntity.setBookDateOut(date);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 14);
            c.setTimeZone(TimeZone.getTimeZone("CST"));
            date = c.getTime();
            bookLoanEntity.setBookDueDate(date);
            session.save(bookLoanEntity);
            tx.commit();
            result = 1;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        result = -1;
        return result;// if non registered cardID is entered for checking out a book
    }

    // to update book_loan entries when borrower is checking in book


    public boolean updateBookLoan(int loanID) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx=session.beginTransaction();
            BookLoanEntity bookLoanEntity = session.createNativeQuery("SELECT * from BOOK_LOANS where Loan_id=" + loanID, BookLoanEntity.class).getSingleResult();
            Date date = new Date();
            bookLoanEntity.setBookDateIn(date);
            session.merge(bookLoanEntity);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }


    public List<BookLoanEntity> fetchCheckoutBooks() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            //todo where bl.Date_in IS NULL
            List<BookLoanEntity> bookLoans = session.createNativeQuery("SELECT bl.* from BOOK_LOANS bl", BookLoanEntity.class).getResultList();
            return bookLoans;
        } finally {
            session.close();
        }

    }


    public boolean isBookCheckedOut(int loanID) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BookLoanEntity> bookLoanEntity = session.createNativeQuery("SELECT bl.* from BOOK_LOANS bl where bl.Loan_id=" + loanID + " and bl.Date_in IS NULL", BookLoanEntity.class).getResultList();
            if (bookLoanEntity.size()==0) {
                return false;
            } else {
                return true;
            }

        } finally {
            session.close();
        }
    }

}
