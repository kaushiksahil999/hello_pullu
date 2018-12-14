package com.example.demo.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.mail.Mailjet;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import redis.clients.jedis.Jedis;

@Component
public class UserServiceImpl implements IUserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserRepository userRepository;
	@Autowired
	Mailjet mailJet;
	BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();

	@Override
	public User registerUser(User user) {
		String pass = bcpe.encode(user.getPassword());
		user.setPassword(pass);

		return userRepository.save(user);

	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);

	}

	// @Scheduled(cron = "1 * * * * *")
	// public void scheduledTask() {
	// Iterable<User> userList = userRepository.findAll();
	// String password = "abc123";
	//
	// for (User user : userList) {
	// user.setPassword(password);
	// userRepository.save(user);
	//
	// }
	//
	// try {
	// mailJet.sendMail(userList, password);
	// } catch (MailjetException e) {
	// logger.info("Mailjet exception");
	// } catch (MailjetSocketTimeoutException e) {
	//
	// logger.info("MailjetSocketTimeoutException");
	// }
	// }

}
