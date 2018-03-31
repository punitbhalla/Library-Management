package edu.utdallas.controller;

import edu.utdallas.controller.model.Status;
import edu.utdallas.daolayer.entities.BorrowerEntity;
import edu.utdallas.librarybusinesslayer.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @RequestMapping(method =RequestMethod.POST,path="/addborrower")
    @CrossOrigin
    @ResponseBody
    public Status addBorrower(@RequestParam String ssn, @RequestParam String bname, @RequestParam String address, @RequestParam String phoneNumber){

        Status status=new Status();
        if(borrowerService.createBorrower(ssn, bname, address, phoneNumber)){
            status.setStatusCode(200);
            status.setMessage("BORROWER IS CREATED SUCCESSFULLY.");
        }
        else{
            status.setStatusCode(417);
            status.setMessage("SORRY! BORROWER WITH THIS SSN ALREADY EXISTS.");
        }
        return status;
    }


    @RequestMapping(method =RequestMethod.GET,path="/getborrower")
    @CrossOrigin
    @ResponseBody
    public Status getBorrowers(){

        Status status=new Status();
        List<BorrowerEntity> borrowerEntities =borrowerService.getBorrowerList();
        if(borrowerEntities.size()==0){
            status.setStatusCode(417);
            status.setMessage("NO BORROWERS FOUND.");
        }
        else{
            status.setStatusCode(200);
            status.setMessage("BORROWERS FOUND");
            status.setPayLoad(borrowerEntities);
        }
        return status;
    }
}
