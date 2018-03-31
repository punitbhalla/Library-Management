package edu.utdallas.daolayer.dao;

import edu.utdallas.daolayer.entities.AuthorEntity;
import edu.utdallas.daolayer.entities.BookEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class AuthorDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<AuthorEntity> getAuthorList() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<AuthorEntity> authorList = session.createNativeQuery("SELECT * FROM AUTHORS", AuthorEntity.class).getResultList();
            return authorList;
        } finally {
            session.close();
        }
    }


    public List<BookEntity> getBooks(String authorName) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            List<BookEntity> authors = session.createNativeQuery("select b.* from BOOK b where b.Isbn in (select ba.Isbn from AUTHORS a, BOOK_AUTHORS ba where ba.Author_id=a.Author_id and ba.Name='" + authorName + "')", BookEntity.class).getResultList();
            return authors;

        } finally {
            session.close();
        }


    }

}
