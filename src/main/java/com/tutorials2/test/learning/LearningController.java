package com.tutorials2.test.learning;

import com.tutorials2.test.apiresponse.ApiResponse;
import com.tutorials2.test.models.User;
import com.tutorials2.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.tutorials2.test.user.Password;

@Service
@RestController
@RequestMapping("/v1")
public class LearningController {
    @Autowired
    Password passObj;
    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String testAction(@RequestParam Map<String,String> params){
        if(!params.containsKey("email") || !params.containsKey("password") || !params.containsKey("full_name")){
            return "email, password, and full_name are required!!";
        }
        if(!passObj.validate(params.get("password"))){
            return "password is invalid!!";
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd : HH-mm-ss");
        String timeStamp = now.format(formatter);

        User user = new User();
        user.setEmail(params.get("email"));
        user.setPassword(params.get("password"));
        user.setFull_name(params.get("full_name"));
        user.setCreated_at(timeStamp);

        String res = userService.createUser(user);
        return res;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody Map<String, String> params){
        List<User> res = userService.findByEmail(params.get("email"));
        if(res.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(false, null, "User not found. Kindly signup first!!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, res, null), HttpStatus.OK);
    }
}
