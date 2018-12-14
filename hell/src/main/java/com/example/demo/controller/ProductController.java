package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DaoResponse;
import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import com.example.demo.service.IRedis;

@RestController
@RequestMapping("/api1")
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private IProductService productService;
	@Autowired
	private IRedis redisUtil;

	@PostMapping("/add")
	public DaoResponse addProduct(HttpServletRequest req, @RequestBody Product product) {
		DaoResponse daoResponse = new DaoResponse();
		Object token = redisUtil.getValue(req.getHeader("Authorization"));
		if (token == null) {
			daoResponse.setCode(HttpStatus.UNAUTHORIZED.value());
			daoResponse.setMessage("Token expired");
			daoResponse.setToken(token);
			logger.info("Token expired");
			return daoResponse;
		} else {
			productService.saveProduct(product);
			daoResponse.setCode(HttpStatus.OK.value());
			daoResponse.setMessage("Product added");
			daoResponse.setToken(token);
			logger.info("Product added");
			return daoResponse;
		}

	}

	@DeleteMapping("/delete/{id}")
	public DaoResponse deleteUser(HttpServletRequest req, @PathVariable(value = "id") Integer id) {
		DaoResponse daoResponse = new DaoResponse();
		Object token = redisUtil.getValue(req.getHeader("Authorization"));
		if (token == null) {
			daoResponse.setCode(HttpStatus.UNAUTHORIZED.value());
			daoResponse.setMessage("Token expired");
			daoResponse.setToken(null);
			logger.info("Token expired");
			return daoResponse;
		} else {
			productService.deleteProduct(id);
			daoResponse.setCode(HttpStatus.OK.value());
			daoResponse.setMessage("Product deleted");
			daoResponse.setToken(token);
			logger.info("Product deleted");
			return daoResponse;
		}

	}

	@PutMapping("/update/{id}")
	public DaoResponse update(HttpServletRequest req, @PathVariable(value = "id") Integer id,
			@RequestBody Product product) {

		DaoResponse daoResponse = new DaoResponse();
		Object token = redisUtil.getValue(req.getHeader("Authorization"));
		if (token == null) {
			daoResponse.setCode(HttpStatus.UNAUTHORIZED.value());
			daoResponse.setMessage("Token expired");
			daoResponse.setToken(null);
			logger.info("Token expired");
			return daoResponse;
		} else {
			productService.updateProduct(id, product);
			daoResponse.setCode(HttpStatus.OK.value());
			daoResponse.setMessage("Product updated");
			daoResponse.setToken(token);
			logger.info("Product updated");
			return daoResponse;
		}
	}
}
