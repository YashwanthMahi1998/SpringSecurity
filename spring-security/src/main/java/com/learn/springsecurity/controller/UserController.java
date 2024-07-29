package com.learn.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springsecurity.entity.AuthRequest;
import com.learn.springsecurity.entity.UserInfo;
import com.learn.springsecurity.service.JwtService;
import com.learn.springsecurity.service.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Hello ,Welcome this endpoint is not secured";
	}
	
	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody UserInfo userInfo) {
		return userInfoService.addUser(userInfo);
	}
	
	@GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    	if(authentication.isAuthenticated()) {
    		return jwtService.generateToken(authRequest.getUsername());
    	} else {
    		throw new UsernameNotFoundException("Invalid User Request");
    	}
    }
	 
	
}
