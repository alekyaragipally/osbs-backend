package com.springsecurity.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.demo.service.OSBSService;

@RestController
public class TokenController {

	@Autowired
	OSBSService osbsService;

	/*
	 * @PostMapping("/token") public String getToken(@RequestParam("username") final
	 * String username, @RequestParam("password") final String password){ String
	 * token= osbsService.login(username, password); if(StringUtils.isEmpty(token)){
	 * return "no token found"; } return token; }
	 */
	
}