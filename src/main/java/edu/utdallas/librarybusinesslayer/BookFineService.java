package edu.utdallas.librarybusinesslayer;

import edu.utdallas.daolayer.dao.BookFineDao;
import edu.utdallas.daolayer.dao.BookLoanDao;
import edu.utdallas.daolayer.dao.BorrowerDao;
import edu.utdallas.daolayer.entities.BookLoanEntity;
import edu.utdallas.daolayer.entities.BorrowerEntity;
import edu.utdallas.daolayer.entities.FinesEntity;
import edu.utdallas.librarybusinesslayer.transferobjects.FinesTO;
import edu.utdallas.librarybusinesslayer.transferobjects.FinesTO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookFineService {

    @Autowired
    private BookFineDao bookFineDao;
    @Autowired
    private BookLoanDao bookLoanDao;
    @Autowired
    private BorrowerDao borrowerDao;

    public List<FinesEntity> getBookFines() {
        return bookFineDao.getBookFines();
    }


    public List<FinesEntity> getBorrowerFines(int cardID) {
        return bookFineDao.getBorrowerFines(cardID);
    }

    public boolean manageFines() {
        List<BookLoanEntity> loanEntities = bookLoanDao.fetchCheckoutBooks();
        Date date = new Date();
        boolean flag = false;
        for (BookLoanEntity loanEntity : loanEntities) {
            long days;
            double fine_amt, dueDateTime, dateTime, dateInTime;
            boolean paid;
            if (loanEntity.getBookDateIn() == null) {
                dueDateTime = loanEntity.getBookDueDate().getTime();
                dateTime = date.getTime();
                days = (long) Math.ceil((dateTime - dueDateTime) / (1000 * 3600 * 24));
                if (days >= 1) {
                    fine_amt = days * .25;
                    paid = false;
                    flag = bookFineDao.createBookFine(loanEntity.getBookLoanID(), fine_amt, paid);
                }
            } else {
                List<FinesEntity> finesEntity=bookFineDao.checkFinesRecords(loanEntity.getBookLoanID());
                if(finesEntity.size()!=0) {
                    if (!finesEntity.get(0).isBookFinePaid()) {
                        dateInTime = loanEntity.getBookDateIn().getTime();
                        dueDateTime = loanEntity.getBookDueDate().getTime();
                        dateTime = date.getTime();
                        days = (long) Math.ceil((dateInTime - dueDateTime) / (1000 * 3600 * 24));
                        if (days >= 1) {
                            fine_amt = days * .25;
                            paid = false;
                            flag = bookFineDao.createBookFine(loanEntity.getBookLoanID(), fine_amt, paid);
                        }
                    }
                }
            }

        }
        return flag;//return false if loanEntities is empty
    }

    public int updateAlreadyCheckedInBookFine(int loanID, double amount) {
        double fine_amount;

        if (!bookLoanDao.isBookCheckedOut(loanID)) {
            List<FinesEntity> finesEntity = bookFineDao.checkFinesRecords(loanID);

            if (finesEntity != null) {
                if (!finesEntity.get(0).isBookFinePaid()) {
                    fine_amount = finesEntity.get(0).getBookFineAmount();
                    if (amount > fine_amount) {
                        return 0;
                    } else if (amount == fine_amount) {
                        //finesEntity.setBookFinePaid(true);
                        bookFineDao.fineUpdate(loanID,fine_amount,true);
                        return 1;
                    } else {
                        fine_amount -= amount;
                        bookFineDao.fineUpdate(loanID,fine_amount,false);
                        //finesEntity.setBookFineAmount(fine_amount);
                        //finesEntity.setBookFinePaid(false);
                        return 1;
                    }
                }
            }
            return -2;//if loan id is not present in fines table
        }
        return -1;// book is not returned yet
    }


    public List<FinesTO> summaryBorrowerOutstandingFines() {
        List<BorrowerEntity> borrowerEntities = bookFineDao.getBorrowerCardIDs();
        List<FinesTO> finesTOS = new ArrayList<FinesTO>();
        double outStandingAmount;
        for (BorrowerEntity borrowerEntity : borrowerEntities) {
            FinesTO finesTO = new FinesTO();
            finesTO.setCardID(borrowerEntity.getBorrowerCardId());
            finesTO.setBorrowerName(borrowerEntity.getBorrowerBname());
            List<FinesEntity> finesEntities = bookFineDao.getBorrowerFines(borrowerEntity.getBorrowerCardId());
            outStandingAmount = 0.0;
            for (FinesEntity finesEntity : finesEntities) {
                if (!finesEntity.isBookFinePaid()) {
                    outStandingAmount += finesEntity.getBookFineAmount();
                }
            }
            finesTO.setOutStandingFines(outStandingAmount);
            finesTOS.add(finesTO);
        }
        return finesTOS;
    }

    public List<FinesTO> summaryBorrowerPaidFines() {
        List<BorrowerEntity> borrowerEntities = bookFineDao.getPaidFinesCardIDs();
        List<FinesTO> finesTOS = new ArrayList<FinesTO>();
        double paidAmount;
        for (BorrowerEntity borrowerEntity : borrowerEntities) {
            FinesTO finesTO = new FinesTO();
            finesTO.setCardID(borrowerEntity.getBorrowerCardId());
            finesTO.setBorrowerName(borrowerEntity.getBorrowerBname());
            List<FinesEntity> finesEntities = bookFineDao.getBorrowerFines(borrowerEntity.getBorrowerCardId());
            paidAmount = 0.0;
            for (FinesEntity finesEntity : finesEntities) {
                if (finesEntity.isBookFinePaid()) {
                    paidAmount += finesEntity.getBookFineAmount();
                }
            }
            finesTO.setOutStandingFines(paidAmount);
            finesTOS.add(finesTO);
        }
        return finesTOS;
    }

    // bifurcations of unpaid fines for a particular cardID
    public List<FinesTO1> getUnpaidFines(int cardID){
        List<FinesEntity> finesEntities=bookFineDao.getBorrowerFines(cardID);
        List<FinesTO1> unpaidFines=new ArrayList<FinesTO1>();
        for(FinesEntity finesEntity:finesEntities){
            if(!finesEntity.isBookFinePaid()){
                FinesTO1 finesTO1=new FinesTO1();
                finesTO1.setLoanID(finesEntity.getBookLoanEntity().getBookLoanID());
                finesTO1.setIsbn(finesEntity.getBookLoanEntity().getBookEntity().getBookIsbn());
                finesTO1.setBookTitle(finesEntity.getBookLoanEntity().getBookEntity().getBookTitle());
                finesTO1.setFine(finesEntity.getBookFineAmount());
                unpaidFines.add(finesTO1);
            }
        }
        return unpaidFines;
    }

    public List<FinesTO1> getPaidFines(int cardID){
        List<FinesEntity> finesEntities=bookFineDao.getBorrowerFines(cardID);
        List<FinesTO1> paidFines=new ArrayList<FinesTO1>();
        for(FinesEntity finesEntity:finesEntities){
            if(finesEntity.isBookFinePaid()){
                FinesTO1 finesTO1=new FinesTO1();
                finesTO1.setLoanID(finesEntity.getBookLoanEntity().getBookLoanID());
                finesTO1.setIsbn(finesEntity.getBookLoanEntity().getBookEntity().getBookIsbn());
                finesTO1.setBookTitle(finesEntity.getBookLoanEntity().getBookEntity().getBookTitle());
                finesTO1.setFine(finesEntity.getBookFineAmount());
                paidFines.add(finesTO1);
            }
        }
        return paidFines;
    }

}
