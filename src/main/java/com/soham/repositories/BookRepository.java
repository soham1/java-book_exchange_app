package com.soham.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.soham.models.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	List<Book> findAll();
	Book save(Book book);
	Book findById(Long id);
	
	@Query("SELECT B FROM Book B WHERE B.user.name = :name")
	List<Book> findUserBooks(@Param("name") String name);

	@Query("SELECT B FROM Book B WHERE B.user.name != :name")
	List<Book> findOthersBooks(@Param("name") String name);
	
}