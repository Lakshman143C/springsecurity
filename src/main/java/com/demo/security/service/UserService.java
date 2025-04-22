package com.demo.security.service;

import com.demo.security.entity.User;
import com.demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;
    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllusers(){
        return userRepository.findAll();
    }
}
