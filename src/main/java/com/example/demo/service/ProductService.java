package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constant.ProductCategory;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

public interface ProductService {
	
	Product getProductById(Integer productId);
    
    Integer createProduct(ProductRequest productRequest) throws IOException;
    
    void updateProduct(Integer productId, ProductRequest productRequest);
    
    void deleteProductById(Integer productId);
    
    List<Product> getProductsByCategory(ProductCategory category);
    
  
    public List<Product> getAllProducts();
  
    public List<Product> searchProductsByName(String productName);
//    String saveBase64Image(String base64Image) throws IOException;
}
