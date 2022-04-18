package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.demo.model.persistence.User;
// import org.springframework.security.core.userdetails.User;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.demo.security.Security_Constants.EXPIRATION_TIME;
import static com.example.demo.security.Security_Constants.SECRET;
import static com.example.demo.security.Security_Constants.TOKEN_PREFIX;


public class JWT_Authentication_Filter extends UsernamePasswordAuthenticationFilter {


    //JWT_Authentication_Filter class
    private AuthenticationManager authenticationManager;
    private HttpServletRequest req;
    private HttpServletResponse res;
    private FilterChain chain;
    private Authentication auth;

    //JWT_Authentication_Filter constructors
    public JWT_Authentication_Filter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        this.req = req;
        this.res = res;
        try {
            User credentials;
            credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);



            Authentication authenticate;
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            Collections.unmodifiableList(new ArrayList<>()))
            );


            Authentication authenticate1;
            authenticate1 = authenticate;
            return authenticate1;
                                 //A return statement causes the program control to transfer back to the caller of a method
        } catch (IOException e) {
            throw new RuntimeException(e);//runtime exception
        }
    }






    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        this.req = req;
        this.res = res;
        this.chain = chain;
        this.auth = auth;




        String token;
        JWTCreator.Builder builder;
        builder = JWT.create();
        JWTCreator.Builder builder1;
        builder1 = builder.withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
        JWTCreator.Builder builder2;
        builder2 = builder.withExpiresAt(new Date(EXPIRATION_TIME + System.currentTimeMillis()));
        token = builder
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(Security_Constants.HEADER_STRING, String.format("%s%s", TOKEN_PREFIX, token));//from res
    }
}
