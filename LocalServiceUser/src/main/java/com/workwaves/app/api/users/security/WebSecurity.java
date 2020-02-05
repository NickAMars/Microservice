package com.workwaves.app.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.workwaves.app.api.users.service.UsersService;

/**
 * 
 * @author Nicholas Marsden
 *
 *Spring security with jwt 
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	private Environment environment; 
	private UsersService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(Environment environment, UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.environment = environment;
		this.usersService= usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		// since we are using jwt for user authorization we have to disable cross side's request for jury
		http.csrf().disable();
//		// if this is not do then we would have a blank screen
		http.headers().frameOptions().disable();
		//basic
		http.authorizeRequests().antMatchers("/users/**").permitAll()
		// registering authication 
		.and().addFilter( getAuthenticationFilter() );
		
		//  allowing only ip address from zuul api gateway
		//  http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"));

	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception{
		AuthenticationFilter authenticationFilter = new  AuthenticationFilter( environment, usersService,authenticationManager());
//		authenticationFilter.setAuthenticationManager(authenticationFilter);  // .setAuthenticationManager(authenticationFilter);
		// changes from the default of /user-ls/login to /user-ls/user/login
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		super.configure(auth);
		auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	
}
