
package com.springsecurity.demo.registration.listener;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.springsecurity.demo.entity.UserEntity;
import com.springsecurity.demo.registration.OnRegistrationCompleteEvent;
import com.springsecurity.demo.service.OSBSService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	OSBSService osbsService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.sender}")
	private String sender;

	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		UserEntity user = event.getUser();
		StringBuilder generatedOTP = new StringBuilder();
		SecureRandom secureRandom = new SecureRandom();
		try {
			secureRandom = SecureRandom.getInstance(secureRandom.getAlgorithm());
			for (int i = 0; i < 6; i++) {
				generatedOTP.append(secureRandom.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		osbsService.createVerificationTokenForUser(user, generatedOTP.toString());

		final SimpleMailMessage email = constructEmailMessage(event, user, generatedOTP.toString());
		mailSender.send(email);
	}

	private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final UserEntity user,
			final String otp) {
		final String recipientAddress = user.getEmail();
		final String subject = "OSBS - Registration Confirmation - OTP";
		final String message = "You registered successfully. Use " + otp
				+ " as one time password(OTP) to confirm your registration. Do not share this OTP to anyone for security reasons. The OTP will be valid for 1hour.";
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo("akkialekya1707@gmail.com");
		email.setSubject(subject);
		email.setText(message);
		email.setFrom(sender);
		return email;
	}

}
