package com.tutorials2.test.user;

import org.springframework.beans.factory.annotation.Value;

public class Token {

    @Value("${jwt.secret}")
    private String secretKey;

    protected String generate(){
        return "token";
    }
}
