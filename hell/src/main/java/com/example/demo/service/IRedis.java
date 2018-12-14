package com.example.demo.service;

public interface IRedis{
	public Object getValue(String key);
	public void setValue(String key, String value);
	public void deleteValue(String key);

}
