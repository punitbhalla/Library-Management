package edu.utdallas.daolayer.dao;


import edu.utdallas.daolayer.entities.BorrowerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class BorrowerDao {
    @Autowired
    private SessionFactory sessionFactory;



    public List<BorrowerEntity> getBorrowers() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BorrowerEntity> borrowerList = session.createNativeQuery("SELECT * FROM BORROWER", BorrowerEntity.class).getResultList();
            return borrowerList;
        } finally {
            session.close();
        }
    }


    public boolean createBorrower(String ssn, String bname, String address, String phoneNumber) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction tx=session.beginTransaction();
            BorrowerEntity borrower = new BorrowerEntity();
            borrower.setBorrowerSsn(ssn);
            borrower.setBorrowerBname(bname);
            borrower.setBorrowerAddress(address);
            borrower.setBorrowerPhone(phoneNumber);
            session.save(borrower);
            tx.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

}
