package com.shopping.service;

import com.shopping.entity.User;
import com.shopping.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
    
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }
    
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    public User saveUser(User user) {
        if (user.getId() == null) {
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return user;
    }
    
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }
    
    public boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
} 