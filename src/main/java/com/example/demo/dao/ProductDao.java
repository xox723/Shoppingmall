package com.example.demo.dao;

import java.io.IOException;
import java.util.List;



import com.example.demo.constant.ProductCategory;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;


public interface ProductDao {
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);
	
	List<Product> getProductsByCategory(ProductCategory category);
	
	List<Product> getAllProducts();
	
	List<Product> findProductsByName(String productName);
	
	
}
