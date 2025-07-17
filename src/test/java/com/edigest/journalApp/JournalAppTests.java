package com.edigest.journalApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigest.journalApp.repository.UsersRepoImpl;

@SpringBootTest
class JournalAppTests {

	@Autowired
	private UsersRepoImpl impl;
	
	@Test
	void testSaveNewUser() {
		impl.getUsersForSA(); 
	}

}
