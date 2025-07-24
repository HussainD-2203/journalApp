package com.edigest.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edigest.journalApp.entity.Users;
import com.edigest.journalApp.repository.UsersRepoImpl;

public class UserSchedular {
		
	@Autowired
	private EmailService emailService; 
	
	@Autowired
	private UsersRepoImpl usersRepoImpl;
	
	public void fetchUserAndSendSaMail() {
				
		List<Users> users = usersRepoImpl.getUsersForSA();
		
		
	}
	
}
