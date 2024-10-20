package com.example.demo.controller;

import java.io.File;
import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constant.ProductCategory;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/mall")
public class ProductController {

    @Autowired
    private ProductService productService;

   
    @PostMapping("/Home")
    public String login(Model model) {
       
        List<Product> productList = productService.getAllProducts();

        
        model.addAttribute("productList", productList);

        
        return "product";
    }
    
    @GetMapping("/Home")
    public String Home(Model model) {
       
        List<Product> productList = productService.getAllProducts();

        
        model.addAttribute("productList", productList);

        
        return "product";
    }
    
    @GetMapping("/category")
    public String getProductByCategory(@RequestParam(value = "category", required = false) ProductCategory category, Model model) {
        List<Product> productList;

        if (category != null) {
            // 根據類別查詢產品
            productList = productService.getProductsByCategory(category);
        } else {
            // 如果類別為 null，查詢所有產品或執行其他邏輯
            productList = productService.getAllProducts(); // 假設有一個方法可以查詢所有產品
        }

        // 將類別和產品列表添加到模型中
        model.addAttribute("category", category);
        model.addAttribute("productList", productList);

        // 返回對應的視圖名稱
        return "productcategory";
    }

    // 顯示商品表單
    @GetMapping("/product/create")
    public String showCreateProductForm(@RequestParam("category") ProductCategory category, Model model) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCategory(category); // 預設分類
        model.addAttribute("productRequest", productRequest);
        return "productdetail";  // 返回商品創建表單頁面 productdetail.html
    }

    @PostMapping("/product")
    public String createProduct(@Valid ProductRequest productRequest, 
                                BindingResult bindingResult, 
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "表單信息有誤");
            return "productdetail";
        }

        try {
            // 保存圖片文件
            String imageUrl = null;
            if (productRequest.getImageUrl() != null && !productRequest.getImageUrl().isEmpty()) {
                MultipartFile imageFile = productRequest.getImageUrl();
                // 保存圖片到專案的 static/uploads 目錄
                imageUrl = saveImageToProjectDirectory(imageFile);
            }

            // 設置圖片的 URL
            productRequest.setImagePath(imageUrl); // 使用新的字段 imagePath

            // 創建產品
            Integer productId = productService.createProduct(productRequest);
            if (productId != null) {
                return "redirect:/mall/category?category=" + productRequest.getCategory();
            } else {
                model.addAttribute("errorMessage", "創建商品失敗");
                return "productdetail";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "圖片保存失敗：" + e.getMessage());
            return "productdetail";
        }
    }

   


    private String saveImageToProjectDirectory(MultipartFile imageFile) throws IOException {
        
        String directory = "C:/uploads/";
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

        
        File targetFile = new File(directory + fileName);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        
        imageFile.transferTo(targetFile);

        
        return "/image/" + fileName;
    }





    // 顯示修改商品表單
    @GetMapping("/product/edit/{productId}")
    public String editProduct(@PathVariable Integer productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            model.addAttribute("product", product);
            return "editProductForm";  // 返回編輯商品頁面 editProductForm.html
        } else {
            return "error/404";  // 返回404錯誤頁面
        }
    }

    @PostMapping("/product/edit/{productId}")
    public String updateProduct(@PathVariable Integer productId, ProductRequest productRequest, Model model) {
        try {
            // 處理圖片文件上傳
            String imageUrl = null;
            if (productRequest.getImageUrl() != null && !productRequest.getImageUrl().isEmpty()) {
                MultipartFile imageFile = productRequest.getImageUrl();
                imageUrl = saveImageToProjectDirectory(imageFile);
            }

            // 設置新的圖片路徑
            if (imageUrl != null) {
                productRequest.setImagePath(imageUrl); // 需要在 ProductRequest 中添加 imagePath 字段
            }

            // 更新產品
            productService.updateProduct(productId, productRequest);
            return "redirect:/mall/Home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "圖片保存失敗：" + e.getMessage());
            return "editProductForm";
        }
    }

    



    // 刪除商品
    @GetMapping("/product/delete/{productId}")
    
    public String deleteProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            productService.deleteProductById(productId);
            return "redirect:/mall/category?category=" + product.getCategory();  // 刪除後重定向到該分類的商品列表
        } else {
            return "error/404";  // 返回404錯誤頁面
        }
    }
    
    @GetMapping("/search")
    public String searchProducts(@RequestParam(value = "productName", required = false) String productName, Model model) {
        List<Product> productList;

        if (productName == null || productName.isEmpty()) {
            productList = productService.getAllProducts(); // 如果名稱為空，返回所有商品
        } else {
            productList = productService.searchProductsByName(productName); // 根據名稱模糊查詢商品
        }

        model.addAttribute("productList", productList); // 將結果添加到模型中
        return "searchResults"; // 返回到 searchResults.html 模板進行渲染
    }
    
   
    }


