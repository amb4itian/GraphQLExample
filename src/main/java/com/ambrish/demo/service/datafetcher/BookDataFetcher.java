package com.ambrish.demo.service.datafetcher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ambrish.demo.model.Book;
import com.ambrish.demo.repo.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;


@Component
public class BookDataFetcher implements DataFetcher<Book> {

	@Autowired
	BookRepository bookRepo;
	
	@Override
	public Book get(DataFetchingEnvironment environment) {
		String isn= environment.getArgument("id");
		return bookRepo.findById(isn).get();
	}

}
