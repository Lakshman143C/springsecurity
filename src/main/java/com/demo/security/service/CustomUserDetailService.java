package com.demo.security.service;

import com.demo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailService implements UserDetailsService {

    @Autowired private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User Not Found with Given UserName "+username));
    }
}
