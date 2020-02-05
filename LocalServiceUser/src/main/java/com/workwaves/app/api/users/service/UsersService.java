package com.workwaves.app.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.workwaves.app.api.user.shared.UserDto;


/**
 * 
 * @author Nicholas Marsden
 *
 *extending UserDetailsService for the webSecurity 
 *
 */
public interface UsersService extends UserDetailsService{
	UserDto createUser(UserDto userDetails);
	UserDto getUserDetailsByEmail(String email);
}
