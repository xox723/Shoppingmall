package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // 从 application.properties 中读取路径
    @Value("${imagepath}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /image/** 映射到本地文件路径
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:" + imagePath);  // 注意 "file:" 前缀
    }
}
