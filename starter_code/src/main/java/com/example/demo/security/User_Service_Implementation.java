package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;

@Service
//used to mark the class as a service provider.So overall @Service annotation is used with classes that provide some business functionalities
public class User_Service_Implementation implements UserDetailsService {

    @Autowired
    // @Autowired annotation is used for dependency injection
    private UserRepository userRepository;
    private String username;

    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        this.username = username;
        User user;
        user = userRepository.findByUsername(username);
        if (user != null) {
            //if not null
            org.springframework.security.core.userdetails.User user1;
            user1 = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
            org.springframework.security.core.userdetails.User user11;
            user11 = user1;
            return user11;
           // A return statement causes the program control to transfer back to the caller of a method
        }
        throw new UsernameNotFoundException(username);//throw exception
    }
}