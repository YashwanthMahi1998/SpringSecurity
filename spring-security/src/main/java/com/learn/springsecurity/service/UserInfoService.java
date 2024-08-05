package com.learn.springsecurity.service;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learn.springsecurity.entity.UserInfo;
import com.learn.springsecurity.entity.UserInfoDetails;
import com.learn.springsecurity.repository.UserInfoRepository;
@Service
public class UserInfoService implements UserDetailsService{
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		// TODO Auto-generated method stub
		Optional<UserInfo>userDetail=userInfoRepository.findByName(username);
		//return userDetail.map(UserInfoDetails::new)
	  			//.orElseThrow(()->new UsernameNotFoundException("User Not Found"+username));
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(()-> new UsernameNotFoundException("User Not Found"+username));
	}
	
	public String addUser(UserInfo userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userInfoRepository.save(userInfo);
		return "User Added Successfully";
	}
 
}
