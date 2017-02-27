package com.soham.repositories;

import org.springframework.data.repository.CrudRepository;

import com.soham.models.BookOffer;

public interface BookOfferRepository extends CrudRepository<BookOffer, Long> {
	BookOffer save(BookOffer bookOffer);
}
