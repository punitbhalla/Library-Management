package edu.utdallas.librarybusinesslayer;

import edu.utdallas.daolayer.dao.BookDao;
import edu.utdallas.daolayer.entities.BookEntity;
import edu.utdallas.librarybusinesslayer.transferobjects.BookTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public List<BookTO> getBookList(){
        List<BookEntity> books=bookDao.getBookList();
        List<BookTO> actualBookList = new ArrayList<BookTO>();
        for(BookEntity book: books){
            BookTO bookto = new BookTO();
            bookto.setBookIsbn(book.getBookIsbn());
            bookto.setBookTitle(book.getBookTitle());
            bookto.setBookAuthors(book.getBookAuthors());
            bookto.setAvailable(bookDao.isBookAvailable(book.getBookIsbn()));
            actualBookList.add(bookto);
        }
        return actualBookList;
    }

    public boolean checkBookAvailability(String isbn){
        return bookDao.isBookAvailable(isbn);
    }

    //todo comma seperated list of authors
    public List<BookTO> searchBooks(String queryString){
        List<BookEntity> books = bookDao.searchBookList(queryString);
        List<BookTO> actualBookList = new ArrayList<BookTO>();
        for(BookEntity book: books){
            BookTO bookto = new BookTO();
            bookto.setBookIsbn(book.getBookIsbn());
            bookto.setBookTitle(book.getBookTitle());
            bookto.setBookAuthors(book.getBookAuthors());
            bookto.setAvailable(bookDao.isBookAvailable(book.getBookIsbn()));
            actualBookList.add(bookto);
        }
        return actualBookList;
    }

}
