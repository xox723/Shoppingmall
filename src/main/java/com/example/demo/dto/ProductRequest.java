package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {
    
    @NotNull
    private String productName;
    
    @NotNull
    private ProductCategory category;
    
    private MultipartFile imageUrl; 


    private String imagePath;

    
    @NotNull
    private int price;
    
    @NotNull
    private int stock;
    
    private String description;
    
    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public MultipartFile getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(MultipartFile imageUrl) {
		this.imageUrl = imageUrl;
	}
    
	
	 
	 public String getImagePath() {
	     return imagePath;
	 }

	 public void setImagePath(String imagePath) {
	     this.imagePath = imagePath;
	 }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
