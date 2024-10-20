package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder; // 注入 PasswordEncoder

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean registerUser(RegisterRequest registerRequest) {
        // 检查用户是否已存在
        if (userDao.findByEmail(registerRequest.getEmail()) != null) {
            return false;  // 用户已存在
        }

        // 加密用户密码
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(encodedPassword);

        // 設置創建建時間
        registerRequest.setCreatedTime(LocalDateTime.now());

        // 保存用户信息
        userDao.registerUser(registerRequest);
        return true;
    }

    @Override
    public User loginUser(String email, String password) {
        // 根据 email 查找用户
        User user = userDao.findByEmail(email);

        // 验证密码是否匹配
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;  // 如果用户不存在或密码不匹配，返回 null
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(Integer userId) {
        userDao.deleteUserById(userId);
    }
}
