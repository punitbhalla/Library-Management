package edu.utdallas.librarybusinesslayer;

import edu.utdallas.daolayer.dao.BorrowerDao;
import edu.utdallas.daolayer.entities.BorrowerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerDao borrowerDao;

    public List<BorrowerEntity> getBorrowerList(){
        return borrowerDao.getBorrowers();
    }

    public boolean createBorrower(String ssn,String bname,String address,String phoneNumber){
        /*
        return true if borrower is created
        return false if borrower with same ssn is entered
         */
        return borrowerDao.createBorrower(ssn, bname, address, phoneNumber);
    }

 }
