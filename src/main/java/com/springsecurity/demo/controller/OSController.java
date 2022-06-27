package com.springsecurity.demo.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.demo.entity.UserEntity;
import com.springsecurity.demo.entity.VerificationToken;
import com.springsecurity.demo.service.OSBSService;

@RestController
@CrossOrigin
public class OSController {

	@Autowired
	OSBSService osbsService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserEntity userEntity) {
		if (!osbsService.findByEmail(userEntity.getEmail())) {
			UserEntity entity = osbsService.signup(userEntity);
			return new ResponseEntity<String>(entity.getEmail(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Email already exists", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/confirmRegistration")
	public ResponseEntity<String> confirmRegistration(@RequestParam(name = "otp", required = true) String otp) {
		VerificationToken verificationToken = osbsService.checkIfOTPExists(otp);
		if (verificationToken.getVerificationId() != null) {
			UserEntity user = verificationToken.getUser();
			Calendar cal = Calendar.getInstance();
//			if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//				return new ResponseEntity<String>("OTP Expired", HttpStatus.BAD_REQUEST);
//			} else {
			osbsService.updateUserAsEnabled(user);
			return new ResponseEntity<String>("Registration Successful", HttpStatus.OK);
			// }
		} else {
			return new ResponseEntity<String>("Invalid OTP", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/signin")
	public ResponseEntity<String> signin(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		UserEntity userEntity = osbsService.findByUsernameAndPassword(username, password);
		if (userEntity.getId() != null) {
			if (userEntity.getIsEnabled()) {
				return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Please Activate your account", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Invalid Username or Password", HttpStatus.BAD_REQUEST);
		}
	}

}
