package com.tutorials2.test.repositories;

import com.tutorials2.test.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    List<User> findByEmail(String email);
}
