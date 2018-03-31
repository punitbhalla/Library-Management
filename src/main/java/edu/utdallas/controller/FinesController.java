package edu.utdallas.controller;

import edu.utdallas.controller.model.Status;
import edu.utdallas.daolayer.entities.FinesEntity;
import edu.utdallas.librarybusinesslayer.BookFineService;
import edu.utdallas.librarybusinesslayer.transferobjects.FinesTO;
import edu.utdallas.librarybusinesslayer.transferobjects.FinesTO1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fines")
public class FinesController {

    @Autowired
    private BookFineService bookFineService;

    @RequestMapping(method = RequestMethod.POST,path="/genaratefine")
    @CrossOrigin
    @ResponseBody
    public Status generateFines() {
        Status status = new Status();

        if (bookFineService.manageFines()) {
            status.setStatusCode(200);
            status.setMessage("FINES UPDATED SUCCESSFULLY");
        } else {
            status.setStatusCode(417);
            status.setMessage("AN ERROR HAS OCCURRED WHILE UPDATING FINES");
        }

        return status;
    }

    @RequestMapping(method = RequestMethod.POST,path="/payfine")
    @CrossOrigin
    @ResponseBody
    public Status payFines(@RequestParam int loanID, @RequestParam double amount) {
        Status status = new Status();
        int result = bookFineService.updateAlreadyCheckedInBookFine(loanID, amount);
        /*
        return -1: book is not returned and borrower is trying to pay for fine
        return 1: successfully updated
        return 0: amount entered is greater than fine
        return -2: not a valid loanID
        */

        if (result == -1) {
            status.setStatusCode(417);
            status.setMessage("YOU ARE REQUESTED TO RETURN THE BOOK FIRST AND THEN PAY THE REQUIRED FINE (IF ANY)");
        } else if (result == -2) {
            status.setStatusCode(418);
            status.setMessage("PLEASE ENTER A VALID LOAN ID");
        } else if (result == 0) {
            status.setStatusCode(419);
            status.setMessage("PLEASE ENTER AMOUNT LESS THAN OR EQUAL TO FINE");
        } else {
            status.setStatusCode(200);
            status.setMessage("FINES UPDATED SUCCESSFULLY");
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.GET,path="/paidfinesummary")
    @CrossOrigin
    @ResponseBody
    public Status getPaidFineSummary() {
        Status status = new Status();
        List<FinesTO> fines = bookFineService.summaryBorrowerPaidFines();
        if (fines.size() == 0) {
            status.setStatusCode(417);
            status.setMessage("OOPS! NO RECORDS FOUND");
        } else {
            status.setStatusCode(200);
            status.setMessage("BORROWER'S FINES PAID SUMMARY");
            status.setPayLoad(fines);
        }

        return status;

    }

    @RequestMapping(method = RequestMethod.GET,path="/outstandingsummary")
    @CrossOrigin
    @ResponseBody
    public Status getOutstandingFineSummary() {
        Status status = new Status();
        List<FinesTO> fines = bookFineService.summaryBorrowerOutstandingFines();
        if (fines.size() == 0) {
            status.setStatusCode(417);
            status.setMessage("OOPS! NO RECORDS FOUND");
        } else {
            status.setStatusCode(200);
            status.setMessage("BORROWER'S OUTSTANDING FINE SUMMARY");
            status.setPayLoad(fines);
        }
        return status;
    }

    @RequestMapping(method = RequestMethod.GET,path="/unpaidfines")
    @CrossOrigin
    @ResponseBody
    public Status getUnpaidFines(@RequestParam int cardID) {
        Status status = new Status();
        List<FinesTO1> fines=bookFineService.getUnpaidFines(cardID);
        if(fines.size()==0){
            status.setStatusCode(417);
            status.setMessage("OOPS! NO UNPAID FINES FOUND");
        }
        else{
            status.setStatusCode(200);
            status.setMessage("BORROWER'S DETAILED OUTSTANDING FINES");
            status.setPayLoad(fines);

        }
        return status;
    }

    @RequestMapping(method = RequestMethod.GET,path="/paidfines")
    @CrossOrigin
    @ResponseBody
    public Status getPaidFines(@RequestParam int cardID) {
        Status status = new Status();
        List<FinesTO1> fines=bookFineService.getPaidFines(cardID);
        if(fines.size()==0){
            status.setStatusCode(417);
            status.setMessage("OOPS! NO PAID FINES FOUND");
        }
        else{
            status.setStatusCode(200);
            status.setMessage("BORROWER'S DETAILED PAID FINES");
            status.setPayLoad(fines);

        }
        return status;
    }

}
