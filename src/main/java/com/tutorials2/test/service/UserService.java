package com.tutorials2.test.service;

import com.tutorials2.test.models.User;
import com.tutorials2.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public  void saveUser(User user){
        userRepository.save(user);
    }
    public boolean createUser(User user){
        String email = user.getEmail();
        if(userRepository.existsByEmail(email)){
            return false;
        }
        userRepository.save(user);
        return true;
    }
    public User getUser(String id){
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUser(){
        try {
            return userRepository.findAll();
        }catch (Exception e){
        }
        return Collections.emptyList();
    }

    public boolean deleleUser(String id){
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public List<User> findByEmail(String email){
        List<User> user = userRepository.findByEmail(email);
        return user;
    }
}
