package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserloginRequest;
import com.example.demo.model.User;

public interface UserService {
		
	 	boolean registerUser(RegisterRequest registerRequest);
	    User loginUser(String email, String password);
	    List<User> getAllUsers();
	    void updateUser(User user);
	    void deleteUserById(Integer userId);
}
