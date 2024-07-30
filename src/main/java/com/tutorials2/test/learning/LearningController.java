package com.tutorials2.test.learning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import com.tutorials2.test.user.Password;

@RestController
@RequestMapping("/v1")
public class LearningController {

//    @Autowired
//    Password passObj;
    @GetMapping("/test")
    public String testAction(@RequestParam Map<String,String> params){
        if(!params.containsKey("email") || !params.containsKey("password")){
            return "email and password is required!!";
        }
        Password passObj = new Password();
        if(!passObj.validate(params.get("password"))){
            return "password is invalid!!";
        }
        return "Hello " + params.get("email");
    }

    private HashMap<String, String> preparePayload(){
        return new HashMap<>();
    }
}
