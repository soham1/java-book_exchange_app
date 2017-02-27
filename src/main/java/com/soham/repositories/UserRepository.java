package com.soham.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.soham.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
	User save(User user);
	User findByName(String name);
}