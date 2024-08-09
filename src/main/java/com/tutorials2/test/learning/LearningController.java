package com.tutorials2.test.learning;

import com.tutorials2.test.apiresponse.ApiResponse;
import com.tutorials2.test.models.Gender;
import com.tutorials2.test.models.User;
import com.tutorials2.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.tutorials2.test.user.Password;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/test/url")
    public ResponseEntity<ApiResponse<String>> apiTest(@RequestParam Map<String, String> params){
        try {
            String url = "https://dummy.restapiexample.com/api/v1/employees";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            List<String> list = new ArrayList<>();
            list.add(response);
            return new ResponseEntity<>(new ApiResponse<>(true, list, null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/test/api/post")
    public ResponseEntity<ApiResponse<String>> apiTestPost(@RequestBody Map<String, String> reqBody){
        String url = "https://dummy.restapiexample.com/api/v1/create";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(reqBody, headers);
        try{
            ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity, String.class);
            if(responseEntity.getStatusCode() == HttpStatus.CREATED){
                return new ResponseEntity<>(new ApiResponse<>(true, List.of(responseEntity.getBody()), null),HttpStatus.OK);
            }else{
                return  new ResponseEntity<>(new ApiResponse<>(false, null, "Record not created!!"), responseEntity.getStatusCode());
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(false, null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
