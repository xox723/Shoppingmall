package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.example.demo.constant.ProductCategory;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", nullable = false, length = 128)
    private String productName;

    @Enumerated(EnumType.STRING) 
    @Column(name = "category", length = 32)
    private ProductCategory category;

    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    public Product() {}

    public Product(String productName, ProductCategory category, String imageUrl, int price, int stock, String description, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.productName = productName;
        this.category = category;
        this.imageUrl = imageUrl;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() { // 使用 ProductCategory
        return category;
    }

    public void setCategory(ProductCategory category) { // 使用 ProductCategory
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
