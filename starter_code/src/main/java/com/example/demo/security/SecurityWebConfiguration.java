package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static com.example.demo.security.Security_Constants.SIGN_UP_URL;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@EnableWebSecurity
//it allows Spring to find (it's a @Configuration and, therefore, @Component ) and automatically apply the class to the global WebSecurity
public class SecurityWebConfiguration extends WebSecurityConfigurerAdapter {

    private User_Service_Implementation userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private HttpSecurity http;
    private AuthenticationManagerBuilder auth;

    public SecurityWebConfiguration(User_Service_Implementation userDetailsService,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    protected void configure(HttpSecurity http) throws Exception {
        this.http = http;
        ExceptionHandlingConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer;
        httpSecuritySessionManagementConfigurer = http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(POST, new String[]{SIGN_UP_URL}).permitAll()
                .anyRequest().authenticated()
                //anyRequest(). authenticated() is that any request must be authenticated otherwise my Spring app will return a 401 response
                .and()
                .addFilter(new JWT_Authentication_Filter(authenticationManager()))
                .addFilter(new JWTAuthen_Verification_Filters(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(STATELESS)                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));;
    }

    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager;
        authenticationManager = super.authenticationManagerBean();
        AuthenticationManager authenticationManager1;
        authenticationManager1 = authenticationManager;
        return authenticationManager1;
        //A return statement causes the program control to transfer back to the caller of a method
    }

    @Override
    //@Override annotation informs the compiler that the element is meant to override an element declared in a superclass
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        this.auth = auth;
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, User_Service_Implementation> authenticationManagerBuilderUser_service_implementationDaoAuthenticationConfigurer;
        authenticationManagerBuilderUser_service_implementationDaoAuthenticationConfigurer = auth.parentAuthenticationManager(authenticationManagerBean())
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}