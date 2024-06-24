package com.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.dao.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	
}
