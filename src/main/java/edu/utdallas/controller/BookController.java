package edu.utdallas.controller;

import edu.utdallas.controller.model.Status;
import edu.utdallas.daolayer.entities.BookEntity;
import edu.utdallas.librarybusinesslayer.BookService;
import edu.utdallas.librarybusinesslayer.transferobjects.BookTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;


    @RequestMapping(method = RequestMethod.GET,path="/bookdetails")
    @ResponseBody
    @CrossOrigin
    public Status getBookDetails(){
        Status status = new Status();
        List<BookTO> books = bookService.getBookList();
        if(books.size() == 0){
            status.setMessage("OOPS! NO BOOKS FOUND");
            status.setStatusCode(417);
        }else{
            status.setStatusCode(200);
            status.setPayLoad(books);
        }
        return status;
    }


    @RequestMapping(method = RequestMethod.GET,path="/allbooks")
    @ResponseBody
    @CrossOrigin
    public Status getAllBooks(@RequestParam String searchString){
        Status status=new Status();
        List<BookTO> books=bookService.searchBooks(searchString);
        if(books.size()==0){
            status.setMessage("OOPS! NO BOOKS FOUND");
            status.setStatusCode(417);
        }
        else{
            status.setStatusCode(200);
            status.setMessage("BOOKS FOUND");
            status.setPayLoad(books);
        }
        return status;
    }

}
