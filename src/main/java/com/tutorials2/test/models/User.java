package com.tutorials2.test.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String full_name;
    private String email;
    private String password;
    private String created_at;
    private Gender gender;

    public User(String email, String password, String full_name, String created_at) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }
}
