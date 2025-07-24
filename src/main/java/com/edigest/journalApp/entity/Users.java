package com.edigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	
	@Id
	private String user_id;
	
	@NonNull
	private String username;
	
	@NonNull
	private String password;
	
	@NonNull
	private String email;
	
	@NonNull
	private String sentiment_analysis;
	
	@DBRef
	List<JournalEntry> journalEntries = new ArrayList<>();
	
	List<String> roles = new ArrayList<>();
}
