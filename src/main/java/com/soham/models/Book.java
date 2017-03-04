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
@Table(name="BOOKS")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String poster;

	public Long getId() {
		return id;
	}
	
	@OneToMany(mappedBy="book", cascade=CascadeType.ALL)
	private Set<BookOffer> bookOffers;

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Book(String name, String author, String poster, User user) {
        this.name = name;
        this.author = author;
        this.poster = poster;
        this.user = user;
    }
	
	public Book() {}

	@Override
	public String toString() {
		return String.format("Book[name='%s', author='%s', poster='%s']", name, author, poster);
	}
}
