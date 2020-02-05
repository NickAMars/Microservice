package com.workwaves.app.api.users.ui.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workwaves.app.api.user.shared.UserDto;
import com.workwaves.app.api.users.service.UsersService;
import com.workwaves.app.api.users.ui.model.CreateUserRepomseModel;
import com.workwaves.app.api.users.ui.model.CreateUserRequestModel;
import com.workwaves.app.api.users.ui.model.LoginRequestModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private Environment env;
	
	@Autowired
	private UsersService usersService;
	
	@PostMapping(
			consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
			)
	public ResponseEntity<CreateUserRepomseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);      	
		
		// call services and map response back to response model
		CreateUserRepomseModel  createUserRepomseModel  = modelMapper.map(usersService.createUser(userDto), CreateUserRepomseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createUserRepomseModel);
	}
//	@PostMapping(
//			path="/login",
//			consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
//			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
//			)
//	public ResponseEntity<CreateUserRepomseModel> userLogin(@RequestBody LoginRequestModel loginRequestModel) {
//		
//		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//		
////		UserDto userDto = modelMapper.map(userDetails, UserDto.class);      	
////		
////		// call services and map response back to response model
////		CreateUserRepomseModel  createUserRepomseModel  = modelMapper.map(usersService.createUser(userDto), CreateUserRepomseModel.class);
//		usersService.createUser(userDto);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createUserRepomseModel);
//	}
//	
	
	
	
	
	@GetMapping(path="/status/check")
	public String status() {
		return " user working on..." + env.getProperty("local.server.port");
	}

}
