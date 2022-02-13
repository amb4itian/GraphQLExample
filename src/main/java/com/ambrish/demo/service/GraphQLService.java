package com.ambrish.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.ambrish.demo.model.Book;
import com.ambrish.demo.repo.BookRepository;
import com.ambrish.demo.service.datafetcher.AllBooksDataFetcher;
import com.ambrish.demo.service.datafetcher.BookDataFetcher;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

	@Value("classpath:books.graphql")
	Resource resource;

	private GraphQL graphQL;
	
	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private AllBooksDataFetcher allBooksDataFetcher;

	@Autowired
	private BookDataFetcher bookDataFetcher;

	@PostConstruct
	private void loadSchema() throws IOException {
		
		//load default data
		loadDefaultData();
		
		
		File schemaFile = resource.getFile();

		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private void loadDefaultData() {
		
		bookRepo.deleteAll();
		
		List<Book> books = Stream.of(
				new Book("ISN101", "Book of Clouds", "Kindle Edition", new String[] {"Chloe Aridjis"}, "Nov 2017"),
                new Book("ISN102", "Cloud Arch & Engineering", "Orielly", new String[] {"Peter", "Sam"}, "Jan 2015"),
                new Book("ISN103", "Java 9 Programming", "Orielly", new String[] {"Venkat", "Ram"}, "Dec 2016")
				)
			.collect(Collectors.toList());
		
		bookRepo.saveAll(books);
		
	}
	

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring().type("Query", typeWiring -> typeWiring
				.dataFetcher("allBooks", allBooksDataFetcher).dataFetcher("book", bookDataFetcher)).build();

	}
	
	public GraphQL getGraphQL() {
		return graphQL;
	}

}
