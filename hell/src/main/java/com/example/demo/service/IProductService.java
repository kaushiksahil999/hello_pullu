package com.example.demo.service;

import com.example.demo.model.Product;

public interface IProductService {
	public Product saveProduct(Product product);
	public String deleteProduct(Integer id);
	public String updateProduct(Integer id,Product product);

}
