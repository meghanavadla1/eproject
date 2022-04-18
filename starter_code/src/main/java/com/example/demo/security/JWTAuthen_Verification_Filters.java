package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.demo.security.Security_Constants.*;

@Component
//@Component is an annotation that allows Spring to automatically detect our custom beans
public class JWTAuthen_Verification_Filters extends BasicAuthenticationFilter {
     //JWTAuthen_Verification_Filters
    private AuthenticationManager authManager;
    private HttpServletRequest req;
    private HttpServletResponse res;
    private FilterChain chain;







    //JWTAuthen_Verification_Filters
    public JWTAuthen_Verification_Filters(AuthenticationManager authManager) {
        super(authManager);
        this.authManager = authManager;
    }





    //doFilterInternal
    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        this.req = req;
        this.res = res;
        this.chain = chain;
        String header;
        header = req.getHeader(HEADER_STRING);


        //if header is not null
        if (header != null) {
            if (header.startsWith(TOKEN_PREFIX)) {
                UsernamePasswordAuthenticationToken authentication;
                authentication = getAuthentication(req);



                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(req, res);
            } else
            {
                chain.doFilter(req, res);
                return;
            }
        } else
        {
            chain.doFilter(req, res);
            return;
            //A return statement causes the program control to transfer back to the caller of a method
        }

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        this.req = req;
        String token;
        token = req.getHeader(HEADER_STRING);
        if (token == null) {
            UsernamePasswordAuthenticationToken o;
            o = null;
            return o;
            //A return statement causes the program control to transfer back to the caller of a method
        }
        String user;
        user = JWT.require(HMAC512(SECRET.getBytes())).build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
        usernamePasswordAuthenticationToken = user != null ? new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()) : null;
        return usernamePasswordAuthenticationToken;
        //UsernamePasswordAuthen     //JWTAuthen_Verification_Filters
    //private AuthenticationManager authManager;
    //private HttpServletRequest req;
     //  ticationToken usernamePasswordAuthenticationToken;
     //   usernamePasswordAuthenticationToken = user != null ? new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()) : null;
      //  return usernamePasswordAuthenticationToken;
        //A return statement causes the program control to transfer back to the caller of a method
    }

}