package com.springsecurity.demo.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Calendar;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.springsecurity.demo.entity.UserEntity;
import com.springsecurity.demo.entity.VerificationToken;
import com.springsecurity.demo.registration.OnRegistrationCompleteEvent;
import com.springsecurity.demo.repo.UserRepository;
import com.springsecurity.demo.repo.VerificationTokenRepository;

@Service
@Transactional
public class OSBSServiceImpl implements OSBSService {

	private String secret = "mysecret";

	private Integer iteration = 33;

	private Integer keylength = 256;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VerificationTokenRepository verificationTokenRepository;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Override
	public UserEntity signup(UserEntity userEntity) {
		userEntity.setPassword(this.encode(userEntity.getPassword()));
		UserEntity userEntityFinal = userRepository.save(userEntity);
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEntityFinal));
		return userEntityFinal;
	}

	@Override
	public Boolean findByEmail(String email) {
		Boolean isExists = userRepository.existsByEmail(email);
		return isExists;
	}

	@Override
	public VerificationToken createVerificationTokenForUser(UserEntity user, String otp) {
		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();
//		Date afterAdding5Mins = new Date(timeInSecs + (60 * 60 * 1000));
		return this.verificationTokenRepository.save(VerificationToken.of(null, otp, user));
	}

	@Override
	public VerificationToken checkIfOTPExists(String otp) {
		return verificationTokenRepository.findByToken(otp);
	}

	@Override
	public UserEntity updateUserAsEnabled(UserEntity user) {
		user.setIsEnabled(true);
		return userRepository.save(user);
	}

	private String encode(CharSequence cs) {
		try {
			byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
					.generateSecret(
							new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keylength))
					.getEncoded();
			return Base64.getEncoder().encodeToString(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public UserEntity findByUsernameAndPassword(String email, String password) {
		UserEntity userEntity = userRepository.findByEmailAndPassword(email, this.encode(password));
		return userEntity;
	}

}
