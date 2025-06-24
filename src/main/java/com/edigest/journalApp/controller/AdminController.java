package com.edigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.cache.AppCache;
import com.edigest.journalApp.service.UsersService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AppCache appCache;
	
	//get All
	@GetMapping("/getAllUsers")
	public ResponseEntity<Object> getAllUsers(){
		return usersService.getAllUsers();	
	}
	
	//This method is used to updat	e the cache from database
	@GetMapping("/clearAppCache")
	public void clearAppcache() {
		appCache.init();
	}
}
