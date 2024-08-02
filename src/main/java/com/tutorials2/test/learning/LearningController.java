package com.tutorials2.test.learning;

import com.tutorials2.test.apiresponse.ApiResponse;
import com.tutorials2.test.models.Gender;
import com.tutorials2.test.models.User;
import com.tutorials2.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Value("${jwt.secret}")
    private String secretKey;

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<Boolean>> testAction(@RequestParam Map<String,Object> params){
        System.out.println(secretKey);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>(false, null, null);
        if(!params.containsKey("email") || !params.containsKey("password") || !params.containsKey("full_name")){
            apiResponse.setError("email, password, and full_name are required!!");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        String password = (String)params.get("password");
        if(!passObj.validate(password)){
            apiResponse.setError("password is invalid!!");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        User user = this.prepareUserData(params);
        boolean res = userService.createUser(user);
        if(res) {
            List<Boolean> resp = new ArrayList<>();
            resp.add(res);
            apiResponse.setSuccess(true);
            apiResponse.setData(resp);
        }else{
            apiResponse.setError("User already present.Please login!!");
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody Map<String, String> params){
        List<User> res = userService.findByEmail(params.get("email"));
        ApiResponse<User> apiResponse = new ApiResponse<>(false, null, null);
        if(res.isEmpty()){
            apiResponse.setError("User not found. Kindly signup first!!");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        apiResponse.setSuccess(true);
        apiResponse.setData(res);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    private User prepareUserData(Map<String,Object> params){
        String password = (String)params.get("password");
        String hashedPassword = passObj.hashPassword(password);
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd : HH-mm-ss"));
        User user = new User((String)params.get("email"), hashedPassword, (String)params.get("full_name"), timeStamp);
        if(params.containsKey("gender")){
            String gender = (String)params.get("gender");
            Gender genderEnum = Gender.valueOf(gender);
            user.setGender(genderEnum);
        }
        return user;
    }
}
