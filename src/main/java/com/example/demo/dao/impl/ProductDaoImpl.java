package com.example.demo.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.demo.constant.ProductCategory;
import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.rowmapper.ProductRowMapper;

@Repository
@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
                   + "created_date, last_modified_date "
                   + "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public Integer createProduct(ProductRequest productRequest) {
        
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, "
                   + "created_date, last_modified_date) "
                   + "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, "
                   + ":createdDate, :lastModifiedDate)";

        
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImagePath()); 
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        // 使用 KeyHolder 获取自动生成的键
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }




    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, "
                   + "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate "
                   + "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImagePath());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }



    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Product> getProductsByCategory(ProductCategory category) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
                   + "created_date, last_modified_date "
                   + "FROM product WHERE category = :category";

        Map<String, Object> map = new HashMap<>();
        map.put("category", category.toString());

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }
    
    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, "
                   + "created_date, last_modified_date "
                   + "FROM product";

        return namedParameterJdbcTemplate.query(sql, new ProductRowMapper());
    }
    
    
    @Override
    public List<Product> findProductsByName(String productName) {
        String sql = "SELECT * FROM product WHERE product_name LIKE :productName";
        Map<String, Object> params = new HashMap<>();
        params.put("productName", "%" + productName + "%"); // 使用 LIKE 查詢進行模糊匹配
        return namedParameterJdbcTemplate.query(sql, params, new ProductRowMapper());
    }
}
