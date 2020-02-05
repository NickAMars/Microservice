package com.workwaves.app.api.users.serviceImpl;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.workwaves.app.api.user.shared.UserDto;
import com.workwaves.app.api.users.data.UserEntity;
import com.workwaves.app.api.users.repository.UsersRepository;
import com.workwaves.app.api.users.service.UsersService;


@Service
public class UsersServiceImpl  implements  UsersService{
	
	// DI fields here 
	UsersRepository userRepository;
	BCryptPasswordEncoder  bCryptPasswordEncoder;
	
	public UsersServiceImpl() {	}
	
	
	//Constructor injection 
	@Autowired
	public UsersServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder  bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/**
	 * createUser and saves it in the database 
	 * 
	 */
	@Override
	public UserDto createUser(UserDto userDetails) {
		// TODO Auto-generated method stub
		userDetails.setUserId(UUID.randomUUID().toString());
		// encript user password
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		// passing fields with the same name
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		// use DI to access repository to save information 
		userRepository.save(userEntity);
		// return a UserDto back to the controller
		return modelMapper.map(userRepository.save(userEntity), UserDto.class );
	}


	
	
	
/**
 * spring security method.
 * Logs in user with spring security 
 *  
 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("loadUserByUSername");
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null ) throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>() );
	}


@Override
public UserDto getUserDetailsByEmail(String email) {
	// TODO Auto-generated method stub
	UserEntity userEntity = userRepository.findByEmail(email);
	if(userEntity == null ) throw new UsernameNotFoundException(email);

	
	// making return type from the repository class
	return new ModelMapper().map(userEntity, UserDto.class);
}

}
