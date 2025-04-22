package com.demo.security.controller;

import com.demo.security.dto.AuthRequest;
import com.demo.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JWTUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest request){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),request.getPassword()));
            return jwtUtil.generateToken(request.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
