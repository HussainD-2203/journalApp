package com.edigest.journalApp.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.entity.Users;
import com.edigest.journalApp.service.UsersService;

@RestController
@RequestMapping("/public")
public class PublicController {
		
	@Autowired
	private UsersService usersService;
	
	//create user
	@PostMapping("/createUser")
	public ResponseEntity<Object> createUser(@RequestBody Users user){
		return usersService.createUser(user);
	}
	
	@GetMapping("/healthCheck")
	public ResponseEntity<Object> healthCheck() {
		Map<Object,Object> response = new HashMap<>();
		response.put("message","OK");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}	
	
	
}
