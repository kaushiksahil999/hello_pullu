package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DaoResponse;
import com.example.demo.model.User;
import com.example.demo.service.IRedis;
import com.example.demo.service.IUserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/ap")
public class AuthenticationController {

	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	@Autowired
	IUserService userService;
	@Autowired
	private IRedis redisUtil;
	BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

	@PostMapping("/login")
	public DaoResponse login(@RequestBody User user) {
		DaoResponse daoResponse = new DaoResponse();
		User u = userService.findByEmail(user.getEmail());
		if (bcpe.matches(bcpe.encode(user.getPassword()), u.getPassword())) {
			logger.info("No user found");
			daoResponse.setCode(HttpStatus.UNAUTHORIZED.value());
			daoResponse.setMessage("Wrong email or password");
			daoResponse.setToken(null);
			return daoResponse;
		} else {
			String jwt = null;
			try {
				jwt = Jwts.builder().setSubject("users/TzMUocMF4p").setExpiration(new Date(1300819380))
						.claim("name", "Robert Token Man").claim("scope", "self groups/admins")
						.signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8")).compact();
				redisUtil.setValue(user.getEmail(), jwt);
			} catch (UnsupportedEncodingException e) {

			}
			logger.info("Successfully logged in");
			daoResponse.setCode(HttpStatus.OK.value());
			daoResponse.setMessage("User logged in");
			daoResponse.setToken(jwt);
			return daoResponse;

		}

	}

	@PostMapping("/logout")
	public DaoResponse get(HttpServletRequest req) {
		DaoResponse daoResponse = new DaoResponse();
		Object token = redisUtil.getValue(req.getHeader("Authorization"));
		if (token == null) {
			daoResponse.setCode(HttpStatus.UNAUTHORIZED.value());
			daoResponse.setMessage("Token expired");
			daoResponse.setToken(token);
			logger.info("Token expired");
			return daoResponse;

		} else {
			redisUtil.deleteValue(req.getHeader("Authorization"));
			daoResponse.setCode(HttpStatus.OK.value());
			daoResponse.setMessage("Successfully logged out");
			daoResponse.setToken(token);
			logger.info("Successfully logged out");
			return daoResponse;
		}

	}
}
