package com.edigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.edigest.journalApp.entity.Users;
import com.edigest.journalApp.repository.UsersRepo;

@Component
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UsersRepo usersRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = usersRepo.findByUsername(username);
				
		if(user != null) {
//			List<String> roles = new ArrayList<>();
//			for(String role:user.getRoles()) {
//				role = "ROLE_"+role;
//				roles.add(role);
//			}
			UserDetails userDetails = User.builder()
			    .username(username)
			    .password(user.getPassword())
			    .authorities(user.getRoles().toArray(new String[0]))
			    .build();
			return userDetails;
		}
		
		throw new UsernameNotFoundException("User not Found with username:"+username);
	}
	
}
