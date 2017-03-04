package com.soham.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.soham.models.Book;
import com.soham.models.Offer;

public interface OfferRepository extends CrudRepository<Offer, Long>{

	Offer save(Offer offer);

	@Query("SELECT O FROM Offer O WHERE O.user.id = :userId")
	List<Offer> findMyOffers(@Param("userId") Long userId);
	
	@Query("SELECT DISTINCT O FROM Offer O JOIN O.bookOffers bo WHERE bo.book.user.id = :userId AND O.user.id != :userId")
	List<Offer> findOthersOffers(@Param("userId") Long userId);
	
	@Transactional
    Long deleteById(Long id);

	@Query("SELECT DISTINCT O.id FROM Offer O JOIN O.bookOffers bo WHERE bo.book.id = :bookId")
	List<Long> findByBookId(@Param("bookId") Long bookId);
	
	@Transactional
	void deleteByIdIn(List<Long> offerIds);
		
}
