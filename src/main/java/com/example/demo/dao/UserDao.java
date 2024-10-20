package com.example.demo.dao;

import java.util.List;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;

public interface UserDao {
    Integer registerUser(RegisterRequest registerRequest);
    User findByEmail(String email);
    List<User> findAllUsers();
    void updateUser(User user);
    void deleteUserById(Integer userId);
}
