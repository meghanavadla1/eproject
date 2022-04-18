package com.example.demo.security;

public class Security_Constants {




    public static final String SECRET;
                                       //The final keyword is a non-access modifier used for classes, attributes and methods, which makes them non-changeable
    public static final long EXPIRATION_TIME;
                                       //The final keyword is a non-access modifier used for classes, attributes and methods, which makes them non-changeable
    public static final String TOKEN_PREFIX;
                                        //The final keyword is a non-access modifier used for classes, attributes and methods, which makes them non-changeable
    public static final String HEADER_STRING;
                                         //The final keyword is a non-access modifier used for classes, attributes and methods, which makes them non-changeable

    static {
        TOKEN_PREFIX = "Bearer ";
        EXPIRATION_TIME = 864_000_000;
        SECRET = "oursecretkey";
        HEADER_STRING = "Authorization";
    }



    public static final String SIGN_UP_URL;
    //The final keyword is a non-access modifier used for classes, attributes and methods, which makes them non-changeable



    static {
        SIGN_UP_URL = "/api/user/create";
    }
}