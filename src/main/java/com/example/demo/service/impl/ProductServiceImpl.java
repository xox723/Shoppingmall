package com.example.demo.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.constant.ProductCategory;
import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductDao productDao;
    
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
    
    @Override
    public Integer createProduct(ProductRequest productRequest)  {
//        String imageUrl = null;
        
        // 處理 Base64 格式的圖片
//        if (productRequest.getImageBase64() != null && !productRequest.getImageBase64().isEmpty()) {
//            imageUrl = productDao.saveBase64Image(productRequest.getImageBase64());
//        }
        
        // 將圖片 URL 保存到資料庫
        return productDao.createProduct(productRequest);
    }

    
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
//        String imageUrl = productRequest.getImageUrl();  

        
//        if (productRequest.getImageBase64() != null && !productRequest.getImageBase64().isEmpty()) {
//            try {
//                imageUrl = productDao.saveBase64Image(productRequest.getImageBase64());
//                productRequest.setImageUrl(imageUrl);  // 更新图片 URL
//            } catch (IOException e) {
//                e.printStackTrace();
//                
//            }
//        }

        
        productDao.updateProduct(productId, productRequest);
    }
    
    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
    
    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
        return productDao.getProductsByCategory(category);
    }
    
    
    @Override
    public List<Product> getAllProducts() {
       
        return productDao.getAllProducts();
    }
    
    
    
    @Override
    public List<Product> searchProductsByName(String productName) {
        return productDao.findProductsByName(productName);
    }
    
//    @Override
//    public String saveBase64Image(String base64Image) throws IOException {
//        return productDao.saveBase64Image(base64Image);
//    }
}
