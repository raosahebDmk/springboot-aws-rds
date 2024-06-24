package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dao.Book;
import com.demo.repo.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookRepository bookRepository;
	
	@PostMapping
	public Book saveBook(@RequestBody Book book)
	{
		System.out.println("saving data : "+book.getClass().getName());
		return bookRepository.save(book);
	}
	
	@GetMapping
	public List<Book> getBooks()
	{
		return bookRepository.findAll();
	}
	
	
	
	
}
