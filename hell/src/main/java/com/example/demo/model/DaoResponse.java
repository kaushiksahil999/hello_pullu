package com.example.demo.model;

import java.io.Serializable;

public class DaoResponse implements Serializable{

	private static final long serialVersionUID = 2460644147611379383L;
	
	private Integer code;
	private String message;
	private Object token;
	private Object response;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getToken() {
		return token;
	}
	public void setToken(Object token) {
		this.token = token;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	
}
