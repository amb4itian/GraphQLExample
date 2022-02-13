package com.ambrish.demo.service.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ambrish.demo.model.Book;
import com.ambrish.demo.repo.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>>{

	@Autowired
	BookRepository bookRepo;
	
	@Override
	public List<Book> get(DataFetchingEnvironment environment) {
		return bookRepo.findAll();
	}

}
