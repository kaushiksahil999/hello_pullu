package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DaoResponse;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.IRedis;
import com.example.demo.service.IUserService;

@RestController
@RequestMapping("/api")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private IRedis redis;

	@PostMapping("/add")
	public DaoResponse createUser(@Valid @RequestBody User user) {
		DaoResponse daoResponse = new DaoResponse();
		if (userService.findByEmail(user.getEmail()) != null) {
			logger.info("Email already registered");
			
			daoResponse.setCode(HttpStatus.NOT_ACCEPTABLE.value());
			daoResponse.setMessage("Email already registered");
			daoResponse.setToken(null);
			return daoResponse;
			
		}

		if (userService.registerUser(user) != null) {
			logger.info("User registered successfully");
			daoResponse.setCode(HttpStatus.OK.value());
			daoResponse.setMessage("User registered successfully");
			daoResponse.setToken(null);
			return daoResponse;
		} else {
			logger.info("Failed to register");
			daoResponse.setCode(HttpStatus.UNAUTHORIZED.value());
			daoResponse.setMessage("Failed to register");
			daoResponse.setToken(null);
			return daoResponse;
			
		}

	}

	@PostMapping("/changepassword")
	public DaoResponse changePassword(HttpServletRequest req, @RequestBody User user) {
		DaoResponse daoResponse = new DaoResponse();
		User user1 = userService.findByEmail(req.getHeader("Authorization"));
		Object token = redis.getValue(req.getHeader("Authorization"));
		user1.setPassword(user.getPassword());
		userService.registerUser(user1);
		daoResponse.setCode(HttpStatus.OK.value());
		daoResponse.setMessage("Password update");
		daoResponse.setToken(token);
		logger.info("Password updated");
		return daoResponse;

	}

}
