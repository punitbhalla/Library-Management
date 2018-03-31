package edu.utdallas.librarybusinesslayer;

import edu.utdallas.daolayer.dao.AuthorDao;
import edu.utdallas.daolayer.entities.AuthorEntity;
import edu.utdallas.daolayer.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
private AuthorDao authorDao;


    public List<AuthorEntity> getAuthorList(){
        return authorDao.getAuthorList();
    }

    public List<BookEntity> getBookList(String authorName){
      return  authorDao.getBooks(authorName);
    }

}
