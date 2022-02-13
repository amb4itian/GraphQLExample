package com.ambrish.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ambrish.demo.service.GraphQLService;

import graphql.ExecutionResult;

@RequestMapping
@RestController
public class BookResource {

	@Autowired
	private GraphQLService graphQLService;
	
	@PostMapping("/rest/books")
	public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
		
		ExecutionResult exe = graphQLService.getGraphQL().execute(query);
		return new ResponseEntity<>(exe,HttpStatus.OK);
	}
	//some commit testing
	
//	allBooks {
//		isn
//		title
//	}
//	
	
//	book(id:"ISN102"){
//		isn 
//		title
//	}
	
	
	@PostMapping
	public String test() {
		return "It's working";
	}
}
