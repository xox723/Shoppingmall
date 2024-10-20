package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize ->
                authorize
//                    .requestMatchers("/user/management/**").hasRole("Admin") 
                    .requestMatchers("/product/**", "/user/**", "/register", "/**").permitAll() // 允许访问的路径
                    .anyRequest().authenticated() 
            )
            .csrf(csrf -> csrf.disable()) // 禁用 CSRF
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 根据需要创建会话
            )
            .formLogin(form -> form
                .loginPage("/user/login") // 自定义的登录页面
                .loginProcessingUrl("/user/login") // 登录表单提交的路徑 觸發Manager
                .usernameParameter("email") // 将用户名参数设置为 email
                .passwordParameter("password") // 保持密码参数为 password
                .defaultSuccessUrl("/mall/Home") // 登录成功后的默认页面
                .failureUrl("/user/login?error=true")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
