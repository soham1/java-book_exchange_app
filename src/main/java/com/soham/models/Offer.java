package com.soham.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="OFFERS")
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="offer", cascade=CascadeType.ALL)
	private Set<BookOffer> bookOffers;

	public Set<BookOffer> getBookOffers() {
		return bookOffers;
	}

	public void setBookOffers(Set<BookOffer> bookOffers) {
		this.bookOffers = bookOffers;
	}

	@Column(nullable = false)
	private String status;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Offer() {}

	@Override
	public String toString() {
		return String.format("Offer[status='%s']", status);
	}

	public Offer(User user) {
		super();
		this.status = "CREATED";
		this.user = user;
	}
}
