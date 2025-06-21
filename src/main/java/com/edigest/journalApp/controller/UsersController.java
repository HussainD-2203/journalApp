package com.edigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.dto.CommonRequestModel;
import com.edigest.journalApp.entity.Users;
import com.edigest.journalApp.service.UsersService;
import com.edigest.journalApp.service.WeatherService;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UsersService usersService;		
	
	@Autowired
	private WeatherService weatherService;
	
	//get user by user name
	@PostMapping("/getByUserName")
	public ResponseEntity<Object> getByUserName(@RequestBody CommonRequestModel request){
		return usersService.getByUserName(request);
	}
	
	//update user
	@PostMapping("/updateUser")
	public ResponseEntity<Object> updateUser(@RequestBody Users user){
		return usersService.updateUser(user);
	}
	
	@PostMapping("/getweather")
	public ResponseEntity<Object> getweather(@RequestBody CommonRequestModel request){
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		return weatherService.getweather(request.getText());
	}
	
}
