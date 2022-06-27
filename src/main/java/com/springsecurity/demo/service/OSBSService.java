package com.springsecurity.demo.service;

import com.springsecurity.demo.entity.UserEntity;
import com.springsecurity.demo.entity.VerificationToken;

public interface OSBSService {

	UserEntity signup(UserEntity userEntity);

	Boolean findByEmail(String email);

	VerificationToken createVerificationTokenForUser(UserEntity user, String string);

	VerificationToken checkIfOTPExists(String otp);

	UserEntity updateUserAsEnabled(UserEntity user);
	
	UserEntity findByUsernameAndPassword(String email, String password);
	

}
