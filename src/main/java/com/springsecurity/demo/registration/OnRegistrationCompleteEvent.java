
package com.springsecurity.demo.registration;

import org.springframework.context.ApplicationEvent;

import com.springsecurity.demo.entity.UserEntity;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final UserEntity user;

	public OnRegistrationCompleteEvent(final UserEntity user) {
		super(user);
		this.user = user;
	}

}
