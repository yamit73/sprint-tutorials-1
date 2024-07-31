package com.tutorials2.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class Password {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean validate(String password){
        int len = password.length();
        if(len < 9 || len > 30){
            return false;
        }
        boolean isNum = false, isSpecChar = false, hasUp = false;
        String specailChars = "!@#$%^&*()-_=+[]{}|;:'\\\",.<>?/`~";
        for(char ch : password.toCharArray()){
            if(Character.isUpperCase(ch)){
                hasUp = true;
            }
            if(Character.isDigit(ch)){
                isNum = true;
            }
            if(specailChars.indexOf(ch) != -1){
                isSpecChar = true;
            }
        }
        return hasUp && isNum && isSpecChar;
    }

    public String hashPassword(String password){
        try {
            return passwordEncoder.encode(password);
        }catch (Exception e){
            throw e;
        }
    }
    public boolean matchPassword(String password, String hashedPassword){
        try {
            return passwordEncoder.matches(password, hashedPassword);
        }catch (Exception e){
            throw e;
        }
    }
}
