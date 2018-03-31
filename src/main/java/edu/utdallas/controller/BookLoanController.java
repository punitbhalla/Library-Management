package edu.utdallas.controller;

        import edu.utdallas.controller.model.Status;
        import edu.utdallas.daolayer.entities.BookLoanEntity;
        import edu.utdallas.librarybusinesslayer.BookLoanService;
        import edu.utdallas.librarybusinesslayer.transferobjects.LoanTO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/bookloan")
public class BookLoanController {
    @Autowired
    private BookLoanService bookLoanService;



    @RequestMapping(method = RequestMethod.GET,path="/getbooksloans")
    @CrossOrigin
    @ResponseBody
    public Status getBooks() {
        Status status = new Status();

        List<LoanTO> loans = bookLoanService.getBookLoans();
        if (loans.size() == 0) {
            status.setStatusCode(417);
            status.setMessage("OOPS! NO RELEVANT RECORD FOUND");
        } else {
            status.setStatusCode(200);
            status.setMessage("BOOK LOANS FOUND");
            status.setPayLoad(loans);
        }

        return status;

    }

    @RequestMapping(method = RequestMethod.GET,path="/getbooks")
    @CrossOrigin
    @ResponseBody
    public Status getBooks(@RequestParam(required = false) String isbn, @RequestParam(required = false) String cardID, @RequestParam(required = false) String borrowerName) {
        Status status = new Status();

        List<LoanTO> bookLoans = bookLoanService.getCheckingBooks(isbn, cardID, borrowerName);
        if (bookLoans.size() == 0) {
            status.setStatusCode(417);
            status.setMessage("OOPS! NO RELEVANT RECORD FOUND");
        } else {
            status.setStatusCode(200);
            status.setMessage("BOOK LOANS FOUND");
            status.setPayLoad(bookLoans);
        }

        return status;

    }

    @RequestMapping(method = RequestMethod.POST,path="/bookcheckout")
    @CrossOrigin
    @ResponseBody
    public Status addBookLoan(@RequestParam String isbn,@RequestParam int cardID){
        Status status = new Status();

//        return -1: exception
//        return 1: successfully inserted
//        return 0: book is already checked out
//        return -2: if borrower is trying to checkout more than 3 books

        int result=bookLoanService.insertBookLoan(isbn, cardID);
        if(result==0){
            status.setStatusCode(417);
            status.setMessage("SORRY! BOOK IS ALREADY CHECKED OUT");
        }
        else if(result==-2){
            status.setStatusCode(418);
            status.setMessage("SORRY! YOU CANNOT CHECKOUT MORE THAN 3 BOOKS");
        }
        else if(result==-1){
            status.setStatusCode(419);
            status.setMessage("NOT A VALID BORROWER CARD ID");
        }
        else{
            status.setStatusCode(200);
            status.setMessage("BOOK IS CHECKED OUT SUCCESSFULLY");
        }

        return status;
    }

    @RequestMapping(method = RequestMethod.POST,path="/bookcheckin")
    @CrossOrigin
    @ResponseBody
    public Status updateBookLoan(@RequestParam int loanID){
        Status status=new Status();
        if(bookLoanService.updateBookLoan(loanID)){
            status.setStatusCode(200);
            status.setMessage("BOOK IS RETURNED SUCCESSFULLY");
        }
        else {
            status.setStatusCode(417);
            status.setMessage("SOME ERROR HAS OCCURRED WHILE RETURNING BOOK");
        }
        return status;
    }

}
