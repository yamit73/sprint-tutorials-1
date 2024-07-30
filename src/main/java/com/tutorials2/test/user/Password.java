package com.tutorials2.test.user;

public class Password {
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
}
