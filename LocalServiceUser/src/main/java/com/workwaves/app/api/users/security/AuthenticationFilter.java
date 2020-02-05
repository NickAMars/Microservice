package com.workwaves.app.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workwaves.app.api.user.shared.UserDto;
import com.workwaves.app.api.users.service.UsersService;
import com.workwaves.app.api.users.ui.model.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private UsersService usersService;
	private Environment env;
	private AuthenticationManager authenticationManager;
	
	AuthenticationFilter(
			Environment env,
			UsersService usersService,
			AuthenticationManager authenticationManager){
		super.setAuthenticationManager(authenticationManager);
		this.env = env;
		this.usersService = usersService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		try {
			
			System.out.println("AuthenticationFilter");
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
//			System.out.println("AuthenticationFilter");
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
						creds.getEmail(),
						creds.getPassword(),
						new ArrayList<>())
					);
		}catch(IOException ex) {
			throw new RuntimeException(ex);
		}
		
//		return super.attemptAuthentication(req, res);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("successfulAuthentication");
		// getting value from spring security 
		String userName = ((User) auth.getPrincipal()).getUsername();
		// we made the spring security accept email instead of 
		UserDto userDetails = usersService.getUserDetailsByEmail(userName);
		
		// generating the JWT token
		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
				.compact();
		
		res.addHeader("token" , token);
		res.addHeader("userId", userDetails.getUserId());
		
	}

	
	
	
	
}
