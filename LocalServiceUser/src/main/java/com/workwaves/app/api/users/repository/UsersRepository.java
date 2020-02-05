package com.workwaves.app.api.users.repository;

import org.springframework.data.repository.CrudRepository;
import com.workwaves.app.api.users.data.UserEntity;

public interface UsersRepository 
extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}

