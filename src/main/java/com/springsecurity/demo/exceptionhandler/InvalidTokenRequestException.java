package com.springsecurity.demo.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenRequestException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4072696668258746333L;
	
	private final String tokenType;
    private final String token;
    private final String message;

    public InvalidTokenRequestException(String tokenType, String token, String message) {
        super(String.format("Invalid [%s] token [%s] : %s", token, tokenType, message));
        this.tokenType = tokenType;
        this.token = token;
        this.message = message;
    }
}