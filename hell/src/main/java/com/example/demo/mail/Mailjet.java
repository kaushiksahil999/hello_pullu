package com.example.demo.mail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

@Component
public class Mailjet {
	Logger logger = LoggerFactory.getLogger(Mailjet.class);

	public void sendMail(Iterable<User> userList, String password)
			throws MailjetException, MailjetSocketTimeoutException {
		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		client = new MailjetClient("0c9922f5cdf0fb548d0ea46526cd3cf6",
				"222b0e5a53a00457c08c577b952c5b5d", new ClientOptions("v3.1"));

		for (User user : userList) {
			request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
					new JSONArray().put(new JSONObject()
							.put(Emailv31.Message.FROM,
									new JSONObject().put("Email", "shweta.kumari@infogain.com").put("Name",
											"Shweta Kumari"))
							.put(Emailv31.Message.TO,
									new JSONArray().put(
											new JSONObject().put("Email", user.getEmail()).put("Name", user.getName())))
							.put(Emailv31.Message.SUBJECT, "Your new password")
							.put(Emailv31.Message.TEXTPART, "Your password has been reset to " + password
									+ " . Please change it to your suitability.")));

			response = client.post(request);
			System.out.println(response.getStatus());
			System.out.println(response.getData());
		}
	}
}
