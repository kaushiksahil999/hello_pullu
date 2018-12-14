package com.example.demo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.IProductService;
@Service
public class ProductServiceImpl implements IProductService {
@Autowired
ProductRepository productRepository;
	@Override
	public Product saveProduct(Product product) {
		
		return productRepository.save(product);
	}

	@Override
	public String deleteProduct(Integer id) {
		productRepository.deleteById(id);
		return "Deleted";
	}

	@Override
	public String updateProduct(Integer id, Product product) {
		Product prod = productRepository.findById(id).orElseThrow(() -> new NullPointerException());
		prod.setName(product.getName());
		prod.setDescription(product.getDescription());
		productRepository.save(prod);
		
		return "Product updated";
	}

}
