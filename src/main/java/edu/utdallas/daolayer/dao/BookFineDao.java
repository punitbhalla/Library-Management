package edu.utdallas.daolayer.dao;

import edu.utdallas.daolayer.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class BookFineDao {
    @Autowired
    private SessionFactory sessionFactory;


    public List<FinesEntity> getBookFines() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<FinesEntity> bookFines = session.createNativeQuery("SELECT * FROM FINES", FinesEntity.class).getResultList();
            return bookFines;
        } finally {
            session.close();
        }
    }


    public boolean createBookFine(int loanID, double amount, boolean paid) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx=session.beginTransaction();
            FinesEntity finesEntity = new FinesEntity();
            BookLoanEntity bookLoanEntity = session.createNativeQuery("SELECT * from BOOK_LOANS where Loan_id=" + loanID, BookLoanEntity.class).getSingleResult();
            finesEntity.setBookLoanEntity(bookLoanEntity);
            finesEntity.setBookFineAmount(amount);
            finesEntity.setBookFinePaid(paid);
            session.saveOrUpdate(finesEntity);
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }


    public List<FinesEntity> checkFinesRecords(int loanID) {

        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<FinesEntity> finesEntity = session.createNativeQuery("SELECT f.* from FINES f where f.Loan_id=" + loanID, FinesEntity.class).getResultList();
            return finesEntity;
        } finally {
            session.close();
        }
    }


    public List<FinesEntity> getBorrowerFines(int cardID) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<FinesEntity> finesEntities = session.createNativeQuery("select f.* from FINES f where f.Loan_id in (select bl.Loan_id from BOOK_LOANS bl where bl.Card_id=" + cardID + ")",FinesEntity.class).getResultList();
            return finesEntities;
        } finally {
            session.close();
        }
    }

    //for outstanding fines

    public List<BorrowerEntity> getBorrowerCardIDs() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BorrowerEntity> borrowerEntities = session.createNativeQuery("SELECT b.* from BORROWER b where b.Card_id IN (SELECT DISTINCT bl.Card_id from BOOK_LOANS bl where bl.Loan_id IN (SELECT f.Loan_id from FINES f where f.Paid=false))", BorrowerEntity.class).getResultList();
            return borrowerEntities;
        } finally {
            session.close();
        }
    }

    //for paid fines

    public List<BorrowerEntity> getPaidFinesCardIDs() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BorrowerEntity> borrowerEntities = session.createNativeQuery("SELECT b.* from BORROWER b where b.Card_id IN (SELECT DISTINCT bl.Card_id from BOOK_LOANS bl where bl.Loan_id IN (SELECT f.Loan_id from FINES f where f.Paid=true))", BorrowerEntity.class).getResultList();
            return borrowerEntities;
        } finally {
            session.close();
        }
    }

    public void  fineUpdate(int loanID,double amount,boolean paid){
        Session session=null;
        try{
           session=sessionFactory.openSession();
           Transaction tx=session.beginTransaction();
           FinesEntity finesEntity=session.createNativeQuery("SELECT * from FINES where Loan_id="+loanID,FinesEntity.class).getSingleResult();
           finesEntity.setBookFinePaid(paid);
           finesEntity.setBookFineAmount(amount);
           session.merge(finesEntity);
           tx.commit();
        }finally {
            session.close();
        }
    }

}
