package com.tutorials2.test.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id; // Make sure you have an @Id field
    private String full_name;
    private String email;
    private String password;
    private String created_at;
    private Gender gender;
    private Address address;
}
