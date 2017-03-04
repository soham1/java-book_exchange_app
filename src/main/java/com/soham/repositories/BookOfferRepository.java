package com.soham.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.soham.models.BookOffer;

public interface BookOfferRepository extends CrudRepository<BookOffer, Long> {
	BookOffer save(BookOffer bookOffer);
	
//	@Query("SELECT DISTINCT bo.offer.id FROM BookOffer bo WHERE bo.book.id = :bookId")
//	List<Long> findByBookId(@Param("bookId") Long bookId);
	
	@Query("SELECT DISTINCT bo.offer.id FROM BookOffer bo WHERE bo.book.id = :bookId")
	List<Long> findByBookId(@Param("bookId") Long bookId);


}
