package com.demo.security.controller;

import com.demo.security.entity.User;
import com.demo.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            User registerUser = userService.registerUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Failed to register the user");
        }
        return new ResponseEntity<>("Failed To Register the user",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAlluser(){

        try {
            return new ResponseEntity<>(userService.getAllusers(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
