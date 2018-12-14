package com.example.demo.service;

import com.example.demo.model.User;

public interface IUserService {

	public User registerUser(User user);
	public User findByEmail(String email);
	public User findByEmailAndPassword(String email,String password);
}
