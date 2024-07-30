package com.tutorials2.test.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "users")
public class User {
    @Id
    private String id;
    private String full_name;
    private String email;
    private String password;
    private String created_at;
}
