package com.edigest.journalApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edigest.journalApp.dto.CommonRequestModel;
import com.edigest.journalApp.dto.ResponseHeaderModel;
import com.edigest.journalApp.entity.Users;
import com.edigest.journalApp.repository.UsersRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersService {

	@Autowired
	private UsersRepo usersRepo;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public ResponseEntity<Object> createUser(Users user) {
		try {	
			Users check = usersRepo.findByUsername(user.getUsername());
			if(check != null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("400"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
				headerModel.setErrMsg("Username already exists");
				log.error("Username {} already exists",check.getUsername());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			//user.setRoles(Arrays.asList("USER"));
			Users users = usersRepo.save(user);
			if(users == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("400"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
				headerModel.setErrMsg("Failed to create User");
				log.info("Failed to create User");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
			}
			else {
				log.info( "User created succesfully {}",users.toString());
				return ResponseEntity.status(HttpStatus.CREATED).body(users);
			}
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}

	public ResponseEntity<Object> getAllUsers(){
		try {
			List<Users> users = usersRepo.findAll();
			if(users.isEmpty()) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("404"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.NOT_FOUND.toString());
				headerModel.setErrMsg("Failed to find Users");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
			}
			List<Map<String, Object>> usersList = new ArrayList<>(); // Create a list to hold user data

	        for (Users user : users) {
	            Map<String, Object> userMap = new HashMap<>(); // Create a new map for each user
	            userMap.put("user_id", user.getUser_id());
	            userMap.put("username", user.getUsername());
	            userMap.put("password", user.getPassword());
	            userMap.put("roles", user.getRoles());
	            userMap.put("email", user.getEmail());
	            userMap.put("sentiment_analysis", user.getSentiment_analysis());
	            usersList.add(userMap); // Add each user map to the list
	        }
	        return ResponseEntity.status(HttpStatus.OK).body(usersList);
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}

	// get user by userName
	public ResponseEntity<Object> getByUserName(CommonRequestModel request) {
		try {
			Users user = usersRepo.findByUsername(request.getUsername()); 
			if(user == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("404"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.NOT_FOUND.toString());
				headerModel.setErrMsg("Failed to find Users");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
			}
			else {
				Map<String, Object> userMap = new HashMap<>(); // Create a new map for each user
	            userMap.put("user_id", user.getUser_id());
	            userMap.put("username", user.getUsername());
	            userMap.put("password", user.getPassword());
	            userMap.put("roles", user.getRoles());
	            userMap.put("email", user.getEmail());
	            userMap.put("sentiment_analysis", user.getSentiment_analysis());
				return ResponseEntity.status(HttpStatus.OK).body(userMap);
			}
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}		
	}
	
	//update user	
	public ResponseEntity<Object> updateUser(Users body) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			Users users = usersRepo.findByUsername(username); 
			if(users == null) {
				ResponseHeaderModel headerModel = new ResponseHeaderModel();
				headerModel.setRespCode("404"); 
				headerModel.setStatusMsg("Failed");
				headerModel.setErr(HttpStatus.NOT_FOUND.toString());
				headerModel.setErrMsg("Failed to find Users");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(headerModel);
			}
			else {
				users.setUsername(body.getUsername());
				users.setPassword(passwordEncoder.encode(body.getPassword()));
				users.setRoles(body.getRoles());
				users.setEmail(body.getEmail());
				users.setSentiment_analysis(body.getSentiment_analysis());
				Users updated = usersRepo.save(users);
				Map<String, Object> userMap = new HashMap<>(); // Create a new map for each user
	            userMap.put("user_id", updated.getUser_id());
	            userMap.put("username", updated.getUsername());
	            userMap.put("password", updated.getPassword());
	            userMap.put("roles", updated.getRoles());
	            userMap.put("email", updated.getEmail());
	            userMap.put("sentiment_analysis", updated.getSentiment_analysis());	            
				return ResponseEntity.status(HttpStatus.OK).body(userMap);
			}
		}
		catch(Exception e) {
			ResponseHeaderModel headerModel = new ResponseHeaderModel();
			headerModel.setRespCode("400"); 
			headerModel.setStatusMsg("Failed");
			headerModel.setErr(HttpStatus.BAD_REQUEST.toString());
			headerModel.setErrMsg(e.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headerModel);
		}
	}

}
