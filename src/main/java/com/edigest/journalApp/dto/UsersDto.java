package com.edigest.journalApp.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

	private String user_id;

	private String username;

	private String password;

	private String email;

	private boolean sentimentAnalysis;

	List<String> roles = new ArrayList<>();
}
