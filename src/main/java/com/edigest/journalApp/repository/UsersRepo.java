package com.edigest.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalApp.entity.Users;

public interface UsersRepo extends MongoRepository<Users, String>{
		
	public Users findByUsername(String username);
}
