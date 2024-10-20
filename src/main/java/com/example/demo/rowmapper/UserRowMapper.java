package com.example.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.constant.UserGender;
import com.example.demo.constant.UserRole;
import com.example.demo.model.User;

public class UserRowMapper implements RowMapper<User> {
	
	
	// UserRowMapper.java
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	    User user = new User();
	    user.setUserId(rs.getInt("user_id"));
	    user.setUserName(rs.getString("username"));
	    user.setEmail(rs.getString("email"));
	    user.setPassword(rs.getString("password"));
	    user.setBirthday(rs.getDate("birthday").toLocalDate());
	    user.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
	    user.setGender(UserGender.valueOf(rs.getString("gender"))); 
	    user.setRole(UserRole.valueOf(rs.getString("role"))); 
	    user.setAddress(rs.getString("address"));
	    user.setPhone(rs.getString("phone"));

	    return user;
	}

}
