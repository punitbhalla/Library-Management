package edu.utdallas.daolayer.dao;


import edu.utdallas.daolayer.entities.BookEntity;
import edu.utdallas.daolayer.entities.BookLoanEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class BookDao {


    @Autowired
    private SessionFactory sessionFactory;


    public List<BookEntity> getBookList() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BookEntity> bookList = session.createNativeQuery("SELECT * FROM BOOK", BookEntity.class).getResultList();
            return bookList;
        } finally {
            session.close();
        }
    }

    //Redundant
/*
   public List<AuthorEntity> getAuthors(String isbn){
       Session session=null;
       try{
           session=sessionFactory.openSession();
           List<AuthorEntity> authors=session.createNativeQuery("select a.* from AUTHORS a where a.Author_id IN (select ba.`Author_id` from BOOK b, BOOK_AUTHORS ba where ba.Isbn=b.Isbn and ba.Isbn="+isbn+")",AuthorEntity.class).getResultList();
           return authors;

       }
       finally {
           session.close();
       }


   }

*/

    public boolean isBookAvailable(String isbn) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            boolean flag;

            List<BookLoanEntity> borrowedBooks = session.createNativeQuery("select b.* from BOOK_LOANS b where b.Isbn='" + isbn + "' AND b.Date_in IS NULL", BookLoanEntity.class).getResultList();
            if (!borrowedBooks.isEmpty()) {
                flag = false;// book is already checked out
            } else {
                flag = true;//book is available
            }
            return flag;

        } finally {
            session.close();
        }
    }

    public List<BookEntity> searchBookList(String queryString) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BookEntity> books = session.createNativeQuery("(select b.* from BOOK b where b.Isbn like'%" + queryString + "%' or b.Title like '%" + queryString + "%')  UNION (select  b.* from BOOK b,(select ba.Isbn from BOOK_AUTHORS ba,AUTHORS a where a.Author_id=ba.Author_id and a.Name like '%" + queryString + "%') temp where b.Isbn=temp.Isbn)", BookEntity.class).getResultList();
            return books;

        } finally {
            session.close();
        }

    }

}
