package com.edigest.journalApp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.edigest.journalApp.entity.Users;

public class UsersRepoImpl {
	
	@Autowired
	private MongoTemplate mongoTemplate;	
	
	public List<Users> getUsersForSA(){			
		Query query = new Query();// keep in mind proper import of Query object		
		query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"));
		query.addCriteria(Criteria.where("sentiment_analysis").is("YES"));
		List<Users> users = mongoTemplate.find(query, Users.class); 
		//with the help of class which we are in .find() providing spring boot understand 
		//in which collection should the query run
		return users;
	}
	
}
