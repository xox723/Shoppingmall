package com.example.demo.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.UserDao; 
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.rowmapper.UserRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    //  NamedParameterJdbcTemplate
    public UserDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    @Override
    public Integer registerUser(RegisterRequest registerRequest) {
        String sql = "INSERT INTO users (username, email, password, birthday, created_time, gender, role, address, phone) " +
                     "VALUES (:username, :email, :password, :birthday, :created_time, :gender, :role, :address, :phone)";

        
        Map<String, Object> params = new HashMap<>();
        params.put("username", registerRequest.getUserName());
        params.put("email", registerRequest.getEmail());
        params.put("password", registerRequest.getPassword());
        params.put("birthday", registerRequest.getBirthday());
        params.put("created_time", registerRequest.getCreatedTime());
        params.put("gender", registerRequest.getGender().toString()); 
        params.put("role", registerRequest.getRole().toString());    
        params.put("address", registerRequest.getAddress());
        params.put("phone", registerRequest.getPhone());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        try {
            // 使用 queryForObject 方法查询
            return jdbcTemplate.queryForObject(sql, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
          
            return null;
        } 
    }
    
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
        
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET username = :username, email = :email, phone = :phone, role = :role WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUserName());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());
        params.put("role", user.getRole().toString());
        params.put("userId", user.getUserId());
        jdbcTemplate.update(sql, params);
    }
    
    @Override
    public void deleteUserById(Integer userId) {
        String sql = "DELETE FROM users WHERE user_id = :userId"; // 修正為從 users 表中刪除
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        jdbcTemplate.update(sql, map);
    }

}
