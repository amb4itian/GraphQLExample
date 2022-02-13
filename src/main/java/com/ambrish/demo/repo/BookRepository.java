package com.ambrish.demo.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ambrish.demo.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{

}
